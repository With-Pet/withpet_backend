package com.example.withpet_01.api.service.post;

import com.example.withpet_01.api.advice.exception.CNotOwnerException;
import com.example.withpet_01.api.advice.exception.CResourceNotExistException;
import com.example.withpet_01.api.advice.exception.CUserNotFoundException;
import com.example.withpet_01.api.domain.Post;
import com.example.withpet_01.api.domain.User;
import com.example.withpet_01.api.dto.Post.ParamsPost;
import com.example.withpet_01.api.repository.PostRepository;
import com.example.withpet_01.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 게시글을 등록합니다. 게시글의 회원UID가 조회되지 않으면 CUserNotFoundException 처리합니다.
//    @CacheEvict(value = CacheKey.POSTS, key = "#boardName")
//    @ForbiddenWordCheck
    public Post writePost(String id, ParamsPost paramsPost) {
        Post post = new Post(userRepository.findById(id).orElseThrow(CUserNotFoundException::new), paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        return postRepository.save(post);
    }


    // 게시글을 수정합니다. 게시글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    //@CachePut(value = CacheKey.POST, key = "#postId") 갱신된 정보만 캐시할경우에만 사용!
//    @ForbiddenWordCheck
    public Post updatePost(long postId, String id, ParamsPost paramsPost) {
        Post post = getPost(postId);
        User user = post.getUser();
        if (!id.equals(user.getId()))
            throw new CNotOwnerException();

        // 영속성 컨텍스트의 변경감지(dirty checking) 기능에 의해 조회한 Post내용을 변경만 해도 Update쿼리가 실행됩니다.
        post.setUpdate(paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
//        cacheSevice.deleteBoardCache(post.getPostId(), post.getBoard().getName());
        return post;
    }

    // 게시글을 삭제합니다. 게시글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
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
    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(CResourceNotExistException::new);
    }
}
