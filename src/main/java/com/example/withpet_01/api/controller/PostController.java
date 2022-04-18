package com.example.withpet_01.api.controller;

import com.example.withpet_01.api.domain.Post;
import com.example.withpet_01.api.dto.post.ParamsPost;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

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

    @ApiOperation(value = "맡기기 리스트 반환", notes = "맡기기 리스트를 반환한다.")
    @GetMapping(value = "/GetList")
    public ListResult<Post> getList(Pageable pageable, @RequestParam(value = "postType") String postType,
                                    @RequestParam(value = "minTime") LocalDateTime minTime,
                                    @RequestParam(value = "maxTime") LocalDateTime maxTime,
                                    @RequestParam(value = "minPrice") String minPrice,
                                    @RequestParam(value = "maxPrice") String maxPrice,
                                    @RequestParam(value = "petType") String petType
    ) {
        return responseService.getPageResult(postService.getFilteredPost(pageable, postType, petType, minTime, maxTime, minPrice, maxPrice));
    }

    @ApiOperation(value = "주소 검색", notes = "주소검색을 통해 주소 리스트를 불러온다.")
    @GetMapping(value = "/SearchAddress")
    public SingleResult<Object> searchAddress(@RequestParam(value = "query") String query) {
        return responseService.getSingleResult(postService.searchAddress(query));
    }

    @ApiOperation(value = "주소 변경", notes = "주소를 선택하여 변경한다.")
    @GetMapping(value = "/AppliedAddress")
    public SingleResult<Object> appliedAddress(@RequestParam(value = "address") String address,
                                             @RequestParam(value = "x") String x,
                                             @RequestParam(value = "y") String y) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName(); // 쓰레드 로컬에서 관리함, userdetail의 값을 사용한다. snsid
        return responseService.getSingleResult(postService.appliedAddress(id, address, x, y));
    }

    @ApiOperation(value = "상세정보 반환", notes = "선택한 게시물의 상세정보를 반환한다.")
    @GetMapping(value = "/returnDetail/{postId}")
    public SingleResult<Post> returnDetail(@PathVariable int postId) {
        return responseService.getSingleResult(postService.returnDetail(postId));
    }

//    @ApiOperation(value = "예약 내역 반환", notes = "예약 상태에 따라 돌봄을 신청한 내역, 돌봄을 받은 내역으로 구분한다." +
//                                                 "B : 이용 전, U : 이용 중, A : 이용 후")
//    @GetMapping(value = "/returnReservation")
//    public ListResult<Post> returnReservation() {
//        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        //String id = authentication.getName(); // 쓰레드 로컬에서 관리함, userdetail의 값을 사용한다. snsid
//        return responseService.getListResult(postService.returnReservation("1"));
//    }

    @ApiOperation(value = "예약 내역 반환", notes = "")
    @GetMapping(value = "/returnReservationDetail/{postId}")
    public SingleResult<Post> returnReservationDetail(int postId) {
        return responseService.getSingleResult(postService.returnReservationDetail(postId));
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
