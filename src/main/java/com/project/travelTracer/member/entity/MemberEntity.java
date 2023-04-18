package com.project.travelTracer.member.entity;

import com.project.travelTracer.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name= "member_table")
public class MemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //unique 제약 조건
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }
}
