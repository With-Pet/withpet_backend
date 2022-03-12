package com.example.withpet_01.api.controller;

import com.example.withpet_01.api.domain.Post;
import com.example.withpet_01.api.dto.Post.ParamsPost;
import com.example.withpet_01.api.dto.login.SingleResult;
import com.example.withpet_01.api.service.ResponseService;
import com.example.withpet_01.api.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"3. Board"})
@RequiredArgsConstructor
@RestController
@RequestMapping
public class PostController {
    private final ResponseService responseService;
    private final PostService postService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")
    @PostMapping(value = "/post")
    public SingleResult<Post> post(@PathVariable String boardName, @Valid @ModelAttribute ParamsPost post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName(); // 쓰레드 로컬에서 관리함, userdetail의 값을 사용한다. snsid
        return responseService.getSingleResult(postService.writePost(id, post));
    }

    @ApiOperation(value = "게시글 상세", notes = "게시글 상세정보를 조회한다.")
    @GetMapping(value = "/post/{postId}")
    public SingleResult<Post> post(@PathVariable long postId) {
        return responseService.getSingleResult(postService.getPost(postId));
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @ApiOperation(value = "게시글 수정", notes = "게시판의 글을 수정한다.")
//    @PutMapping(value = "/post/{postId}")
//    public SingleResult<Post> post(@PathVariable long postId, @Valid @ModelAttribute ParamsPost post) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String uid = authentication.getName();
//        return responseService.getSingleResult(boardService.updatePost(postId, uid, post));
//    }
//
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
//    @DeleteMapping(value = "/post/{postId}")
//    public CommonResult deletePost(@PathVariable long postId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String uid = authentication.getName();
//        boardService.deletePost(postId, uid);
//        return responseService.getSuccessResult();
//    }
}
