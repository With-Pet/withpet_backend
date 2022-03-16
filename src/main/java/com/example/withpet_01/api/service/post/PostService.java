package com.example.withpet_01.api.service.post;

import com.example.withpet_01.api.advice.exception.CNotOwnerException;
import com.example.withpet_01.api.advice.exception.CResourceNotExistException;
import com.example.withpet_01.api.advice.exception.CUserNotFoundException;
import com.example.withpet_01.api.domain.Post;
import com.example.withpet_01.api.domain.User;
import com.example.withpet_01.api.domain.handler.FileHandler;
import com.example.withpet_01.api.dto.Post.ParamsPost;
import com.example.withpet_01.api.dto.main.KaKaoAddressResult;
import com.example.withpet_01.api.dto.main.KaKaoResult;
import com.example.withpet_01.api.repository.PostImageRepository;
import com.example.withpet_01.api.repository.PostRepository;
import com.example.withpet_01.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    /**
     * 전체 게시글을 반환합니다.
     * @param pageable : page, size
     */
    @Transactional
    public Page<Post> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     * 필터링된 게시글을 반환합니다.
     * @param pageable : page, size(15)
     * @param postType : 게시물 타입 "A" : 전체, "W" : 산책, "C" : 돌봄, "E" : 체헙
     * @param petType  : 반려동물 종류 "A" : 전체, "D" : 강아지, "C" : 고양이, "E" : 기타
     * @param minTime  : 최소 날짜
     * @param maxTime  : 최대 날짜
     * @param minPrice : 최소 금액
     * @param maxPrice : 최대 금액
     */
    @Transactional
    public Page<Post> getFilteredPost(Pageable pageable, String postType, String petType, LocalDateTime minTime, LocalDateTime maxTime, String minPrice, String maxPrice) {
        return postRepository.findByPostSTimeGreaterThanAndPostETimeLessThanAndPostPriceBetweenAndPostType(pageable, minTime, maxTime, minPrice, maxPrice, postType);
    }

    /**
     * 게시글을 등록합니다. 게시글의 회원UID가 조회되지 않으면 CUserNotFoundException 처리합니다.
     */
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
                .postPrice(paramsPost.getPostPrice()
                ).build();

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
//    private List<Image> create(List<MultipartFile> files) throws Exception {
//        // 파일 처리를 위한 Board 객체 생성
//        Post post = new Post();
//
//        List<Image> photoList = fileHandler.parseFileInfo(files);
//
//        // 파일이 존재할 때에만 처리
//        if(!photoList.isEmpty()) {
//            for(Image postImage : photoList) {
//                // 파일을 DB에 저장
//                post.addPhoto(postImageRepository.save(postImage));
//            }
//        }
//        return postRepository.save(post).getPostImage();
//    }
    /*
    카카오 주소검색 api로 부터 주소명, 주소타입, x좌푶, y좌표 값을 가져온다.
     */
    @Transactional
    public Object searchAddress(String query) {
        Mono<Object> mono = WebClient.builder().baseUrl("https://dapi.kakao.com")
                .build().get()
                .uri(builder -> builder.path("/v2/local/search/address.json")
                        .queryParam("query", query)
                        .build()
                )
                .header("Authorization", "KakaoAK" + " " + "b6f8901a70131b1f410a4229691dbc19")
                .exchangeToMono(response -> {
                    return response.bodyToMono(KaKaoResult.class);
                });
        return mono.block();
    }

    /**
     * 선택한 주소로 검색 주소 및 x, y 좌표를 변경한다.
     * @param address 주소명
     * @param x       x좌표
     * @param y       y좌표
     */
    @Transactional
    public Object appliedAddress(String id, String address, String x, String y) {
        if(address.equals("gps")){
            Mono<Object> mono = WebClient.builder().baseUrl("https://dapi.kakao.com")
                    .build().get()
                    .uri(builder -> builder.path("/v2/local/geo/coord2address.json")
                            .queryParam("x", x)
                            .queryParam("y", y)
                            .queryParam("input_coord", "WGS84")
                            .build()
                    )
                    .header("Authorization", "KakaoAK" + " " + "b6f8901a70131b1f410a4229691dbc19")
                    .exchangeToMono(response -> {
                        User user = userRepository.findById(id).orElseThrow(CUserNotFoundException::new);
                        user.setSearchX(x);
                        user.setSearchY(y);
                        return response.bodyToMono(KaKaoAddressResult.class);
                    });
            return mono.block();

        }else{
            User user = userRepository.findById(id).orElseThrow(CUserNotFoundException::new);
            user.setSearchLocation(address);
            user.setSearchX(x);
            user.setSearchY(y);
            return user;
        }
    }

    @Transactional
    public Post returnDetail(int postId) {
        Post post = postRepository.findById(postId);
        return post;
    }

    @Transactional
    public List<Post> returnReservation(String id) {
        User user = userRepository.findById(id).orElseThrow(CUserNotFoundException::new);
        return user.getPosts();
    }

    @Transactional
    public Post returnReservationDetail(int postId) {
        Post post = postRepository.findById(postId);
        return post;
    }
}
