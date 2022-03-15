package com.example.withpet_01.api.service.post;

import com.example.withpet_01.api.advice.exception.CNotOwnerException;
import com.example.withpet_01.api.advice.exception.CResourceNotExistException;
import com.example.withpet_01.api.domain.Post;
import com.example.withpet_01.api.domain.Image;
import com.example.withpet_01.api.domain.User;
import com.example.withpet_01.api.domain.handler.FileHandler;
import com.example.withpet_01.api.dto.Post.ParamsPost;
import com.example.withpet_01.api.repository.PostImageRepository;
import com.example.withpet_01.api.repository.PostRepository;
import com.example.withpet_01.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final FileHandler fileHandler;

    //전체 게시글을 반환합니다. 파라미터 : page, size
    @Transactional
    public Page<Post> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    // 게시글을 등록합니다. 게시글의 회원UID가 조회되지 않으면 CUserNotFoundException 처리합니다.
    //@CacheEvict(value = CacheKey.POSTS, key = "#boardName")
    //@ForbiddenWordCheck
    @Transactional
    public Post writePost(
            //String id,
            ParamsPost paramsPost) throws Exception {

        Post post = Post.builder()
                .postAuthor(paramsPost.getPostAuthor())
                .postType(paramsPost.getPostType())
                .postSTime(paramsPost.getPostSTime())
                .postETime(paramsPost.getPostETime())
                .postTitle(paramsPost.getPostTitle())
                .postLocation(paramsPost.getPostLocation())
                .postRequested(paramsPost.getPostRequested())
                .postPrice(paramsPost.getPostPrice()).build();

        return postRepository.save(post);
    }

    //게시글을 수정합니다. 게시글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    //@CachePut(value = CacheKey.POST, key = "#postId") 갱신된 정보만 캐시할경우에만 사용!
    //@ForbiddenWordCheck
    @Transactional
    public Post updatePost(long postId, String id, ParamsPost paramsPost) {
        Post post = getPost(postId);
        User user = post.getUser();
        if (!id.equals(user.getId()))
            throw new CNotOwnerException();

        // 영속성 컨텍스트의 변경감지(dirty checking) 기능에 의해 조회한 Post내용을 변경만 해도 Update쿼리가 실행됩니다.
        post.setUpdate(paramsPost.getPostAuthor(), paramsPost.getPostTitle());
//        cacheSevice.deleteBoardCache(post.getPostId(), post.getBoard().getName());
        return post;
    }

    // 게시글을 삭제합니다. 게시글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    @Transactional
    public boolean deletePost(long postId, String uid) {
        Post post = getPost(postId);
        User user = post.getUser();
        if (!uid.equals(user.getId()))
            throw new CNotOwnerException();
        postRepository.delete(post);
//        cacheSevice.deleteBoardCache(post.getPostId(), post.getBoard().getName());
        return true;
    }


    // 게시글ID로 게시글 단건 조회. 없을경우 CResourceNotExistException 처리
//    @Cacheable(value = CacheKey.POST, key = "#postId", unless = "#result == null")
    @Transactional
    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(CResourceNotExistException::new);
    }

    /*
    List<MultipartFile> 을 전달받아 파일을 저장한 후
    관련 정보를 List<PostImage>로 변환하여 반환할 FileHandler를 생성하고,
    반환받은 파일 정보를 저장하기 위하여 PostService를 수정한다.
    */
    private List<Image> create(List<MultipartFile> files) throws Exception {
        // 파일 처리를 위한 Board 객체 생성
        Post post = new Post();

        List<Image> photoList = fileHandler.parseFileInfo(files);

        // 파일이 존재할 때에만 처리
        if(!photoList.isEmpty()) {
            for(Image postImage : photoList) {
                // 파일을 DB에 저장
                post.addPhoto(postImageRepository.save(postImage));
            }
        }
        return postRepository.save(post).getPostImage();
    }

    @Transactional
    public String searchAddress(String query) {
        Mono<String> mono = WebClient.builder().baseUrl("https://dapi.kakao.com")
                .build().get()
                .uri(builder -> builder.path("/v2/local/search/address.json")
                        .queryParam("query", query)
                        .build()
                )
                .header("Authorization", "KakaoAK"+" "+"b6f8901a70131b1f410a4229691dbc19")
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                });
        return mono.block();
    }
}
