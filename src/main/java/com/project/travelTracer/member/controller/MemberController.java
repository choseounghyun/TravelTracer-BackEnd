package com.project.travelTracer.member.controller;

import com.project.travelTracer.global.common.CommonDetailResponse;
import com.project.travelTracer.global.common.CommonResponse;
import com.project.travelTracer.member.dto.*;
import com.project.travelTracer.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MemberSignUpDto> signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto, BindingResult bindingResult) throws  Exception {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        log.info("info memberSignUPDto={}", memberSignUpDto);
        memberService.signUp(memberSignUpDto);
        return ResponseEntity.ok(memberSignUpDto);
    }

    //회원정보수정
    @PutMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public void updateBasicInfo(@Valid @RequestBody MemberUpdateDto memberUpdateDto) throws Exception {
        memberService.update(memberUpdateDto);
    }

    //비밀번호수정
    @PutMapping("/member/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
        memberService.updatePassword(updatePasswordDto.getCheckPassword(),updatePasswordDto.getTobePassword());
    }

    @DeleteMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(@Valid @RequestBody MemberWithdrawDto memberWithdrawDto) throws Exception {
        memberService.withdraw(memberWithdrawDto.getCheckPassword());
    }

    //회원 정보 조회
    @GetMapping("/member/{id}")
    public ResponseEntity getInfo(@Valid @PathVariable("id") Long id) throws Exception {
        MemberInfoDto info = memberService.getInfo(id);
        return new ResponseEntity(info, HttpStatus.OK);
    }

    //내 회원 정보조회
    @GetMapping("/member")
    public ResponseEntity getMyInfo(HttpServletResponse response) throws Exception {

        MemberInfoDto info = memberService.getMyInfo();
        return new ResponseEntity(info, HttpStatus.OK);
    }

    //회원아이디중복확인
    @GetMapping("/check/{userId}")
    public ResponseEntity<CommonResponse> checkIdDuplicate(@Valid @PathVariable("userId") String userId) throws Exception {
        Boolean check = memberService.checkIdDuplicate(userId);
        if(check == true) {
            return ResponseEntity.ok(new CommonResponse(400, "실패", new CommonDetailResponse<>(check)));
        }
        return ResponseEntity.ok(new CommonResponse(200, "성공", new CommonDetailResponse<>(check)));
    }


}
