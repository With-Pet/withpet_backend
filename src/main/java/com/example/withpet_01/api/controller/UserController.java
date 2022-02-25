package com.example.withpet_01.api.controller;

import com.example.withpet_01.api.advice.exception.CUserNotFoundException;
import com.example.withpet_01.api.domain.User;
import com.example.withpet_01.api.dto.CommonResult;
import com.example.withpet_01.api.dto.SingleResult;
import com.example.withpet_01.api.repository.UserRepository;
import com.example.withpet_01.api.service.ResponseService;
import com.example.withpet_01.api.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping()
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/user")
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

//    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
//    @PostMapping(value = "/user")
//    public User save(@ApiParam(value = "회원이메일", required = true) @RequestParam String email,
//                     @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
//        User user = User.builder()
//                .email(email)
//                .name(name)
//                .build();
//        return userRepository.save(user);
//    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원ID", required = true) @PathVariable long msrl) throws Exception{
        // 결과데이터가 단일건인경우 getBasicResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userRepository.findById(msrl).orElseThrow(Exception::new));
    }

    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        User user = userRepository.findByid(id).orElseThrow(CUserNotFoundException::new);
        user.setName(name);
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
        userRepository.deleteById(msrl);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }

}