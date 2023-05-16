package com.project.travelTracer.member.dto;

import com.project.travelTracer.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberInfoDto {

    private final String id;
    private final String username;
    private final Integer age;


    @Builder
    public MemberInfoDto(Member member) {
        this.id = member.getUserId();
        this.username = member.getUserName();
        this.age = member.getAge();
    }
}
