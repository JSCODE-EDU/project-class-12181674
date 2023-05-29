package com.jscode.boardService.controller;

import com.jscode.boardService.domain.dto.MemberDto;
import com.jscode.boardService.domain.dto.MemberInfoDto;
import com.jscode.boardService.exception.ErrorResponse;
import com.jscode.boardService.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "회원관련 컨트롤러")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "회원가입 기능", notes = "이메일, 패스워드를 입력받아 회원가입 수행")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원가입 성공."),
            @ApiResponse(code = 400, message = "이메일 또는 패스워드가 유효성 검사 실패.", response = ErrorResponse.class)
    })
    public String saveMember(@Valid @RequestBody MemberDto memberDto){

        memberService.save(memberDto);
        return "회원가입에 성공하였습니다.";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "로그인 기능", notes = "이메일, 패스워드를 입력받아 로그인 실시")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공."),
            @ApiResponse(code = 400, message = "이메일 또는 패스워드가 잘못됐다.", response = ErrorResponse.class)
    })
    public String login(@RequestBody MemberDto memberDto){

        String token = memberService.login(memberDto);
        return "로그인에 성공하였습니다.\n" + token;
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public MemberInfoDto getMyInfo(HttpServletRequest request){

        return memberService.getMemberInfo(request);
    }
}
