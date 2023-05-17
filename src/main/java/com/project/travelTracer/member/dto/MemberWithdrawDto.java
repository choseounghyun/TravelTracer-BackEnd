package com.project.travelTracer.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberWithdrawDto {

    @NotBlank(message = "비밀번호를 입력해주세요")
    String checkPassword;

    public MemberWithdrawDto() {

    }

    public MemberWithdrawDto(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}
