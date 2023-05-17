package com.project.travelTracer.member.dto;

import com.project.travelTracer.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberInfoDto {

    private String id;
    private String username;
    private Integer age;

    public MemberInfoDto() {

    }

    @Builder
    public MemberInfoDto(Member member) {
        this.id = member.getUserId();
        this.username = member.getUserName();
        this.age = member.getAge();
    }
}
