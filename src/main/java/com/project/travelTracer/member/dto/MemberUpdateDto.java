package com.project.travelTracer.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class MemberUpdateDto {

    Optional<String> userName;
    Optional<Integer> age;
    Optional<String> userEmail;

    public MemberUpdateDto() {

    }

    public <T> MemberUpdateDto(Optional<String> updateName, Optional<Integer> updateAge, Optional<String> userEmail) {
        this.userName= updateName;
        this.age = updateAge;
        this.userEmail = userEmail;

    }
}
