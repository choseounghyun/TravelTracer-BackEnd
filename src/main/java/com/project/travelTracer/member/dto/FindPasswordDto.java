package com.project.travelTracer.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class FindPasswordDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 7, max = 25, message = "아이디는 7~25자 내외로 입력해주세요")
    String userId;

    @Email
    String userEmail;

    @NotBlank(message = "이름을 입력해주세요") @Size(min=2, message = "사용자 이름이 너무 짧습니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해주세요.")
    String userName;


    public FindPasswordDto(String userId, String userEmail, String userName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
    }
}
