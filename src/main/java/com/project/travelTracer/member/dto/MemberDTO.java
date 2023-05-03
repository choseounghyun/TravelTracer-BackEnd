package com.project.travelTracer.member.dto;

import com.project.travelTracer.member.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {

    private Long id;
    private String memberLoginID;
    private String memberPassword;
    private String memberName;
    private String memberGender;
    private String memberPhoneAgency;
    private String phone1;
    private String phone2;
    private String phone3;
    private String memberEmail;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO= new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberLoginID(memberEntity.getMemberLoginID());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberPhoneAgency(memberEntity.getMemberPhoneAgency());
        memberDTO.setPhone1(memberDTO.getPhone1());
        memberDTO.setPhone1(memberDTO.getPhone2());
        memberDTO.setPhone1(memberDTO.getPhone3());
        memberDTO.setMemberEmail(memberDTO.getMemberEmail());
        return memberDTO;
    }
}


