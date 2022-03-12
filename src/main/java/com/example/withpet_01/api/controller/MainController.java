package com.example.withpet_01.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * Main Controller
 * */
@Api(tags = {"1. Main"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@Validated
public class MainController {

    /**
     * 3.1 서비스등록 api
     * 로컬 회원가입을 한다.
     * @return
     */
    @ApiOperation(value = "1-1 회원가입", notes = "로컬 회원가입을 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "정상적으로 회원가입이 완료되었습니다."),
            @ApiResponse(code = 403, message = "이미 등록된 유저입니다."),
            @ApiResponse(code = 412, message = "필수 항목이 누락되었습니다."),
            @ApiResponse(code = 500, message = "회원가입에 실패하였습니다.")
    })
    @PostMapping(value = "/Join")   //Todo throws 설정
    public ResultDto join(
            @RequestBody @Valid UserJoinDto userJoinDto
    ) throws DuplicateDataException {

        User user = User.builder()
                .usrType(userJoinDto.getUsrType())
                .usrId(userJoinDto.getUsrId())
                .usrPass(userJoinDto.getUsrPass())
                .usrEmail(userJoinDto.getUsrEmail())
                .usrTel(userJoinDto.getUsrTel())
                .usrName(userJoinDto.getUsrName())
                .usrImg(userJoinDto.getUsrImg())
                .build();

        ResultDto resultDto = new ResultDto();
        resultDto.setCode(HttpStatus.OK.value());
        User registeredUser = userService.saveUser(user).get(0);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", registeredUser.getId());
        map.put("usrType", registeredUser.getUsrType());
        map.put("usrEmail", registeredUser.getUsrEmail());

        resultDto.setData(map);
        resultDto.setMessage(HttpStatus.OK.toString());
        return resultDto;
    }
}
