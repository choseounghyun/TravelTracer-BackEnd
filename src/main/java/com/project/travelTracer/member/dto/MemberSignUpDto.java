package com.project.travelTracer.member.dto;

import com.project.travelTracer.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter

public class MemberSignUpDto {

    String userId;

    String userPassword;

    String userName;

    Integer age;

    public MemberSignUpDto(String userId, String userPassword, String userName, Integer age) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.age = age;
    }

    public Member toEntity() {
        return Member.builder().userId(userId)
                .userPassword(userPassword)
                .userName(userName)
                .age(age)
                .build();
    }
}
