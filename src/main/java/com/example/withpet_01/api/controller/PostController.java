package com.example.withpet_01.api.controller;

import com.example.withpet_01.api.domain.Post;
import com.example.withpet_01.api.dto.Post.ParamsPost;
import com.example.withpet_01.api.dto.login.ListResult;
import com.example.withpet_01.api.dto.login.SingleResult;
import com.example.withpet_01.api.repository.UserRepository;
import com.example.withpet_01.api.service.ResponseService;
import com.example.withpet_01.api.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"3. Board"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/main")
public class PostController {
    private final ResponseService responseService;
    private final PostService postService;
    private final UserRepository userRepository;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")
    @PostMapping(value = "/RegisterService")
    public SingleResult<Post> registerService(@RequestBody @Valid ParamsPost post) throws Exception {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String id = authentication.getName(); // 쓰레드 로컬에서 관리함, userdetail의 값을 사용한다. snsid
        return responseService.getSingleResult(postService.writePost(post));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "맡기기 리스트 반환", notes = "맡기기 리스트를 반환한다.")
    @GetMapping(value = "/GetList")
    public ListResult<Post> getList(Pageable pageable) {
        return responseService.getPageResult(postService.getAllPost(pageable));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "주소 검색", notes = "주소검색을 통해 주소 리스트를 불러온다.")
    @GetMapping(value = "/SearchAddress")
    public SingleResult<String> searchAddress(@RequestParam(value = "query") String query) {
        return responseService.getSingleResult(postService.searchAddress(query));
    }


//    @PostMapping("")
//    @ResponseStatus(HttpStatus.CREATED)
//    public int create(
//            @RequestParam(value="image", required=false) List<MultipartFile> files,
//            @RequestParam(value="id") String id,
//            @RequestParam(value="title") String title,
//            @RequestParam(value="content") String content
//    ) throws Exception {
//
//        User user = userRepository.findById(id).orElseThrow(CUserNotFoundException::new);
//
//        ParamsPost requestDto =
//                Post.builder()
//                        .user(user)
//                        .build();
//
//        return postService.create(requestDto, files);
//    }

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
