package com.project.travelTracer.member.entity;

import com.project.travelTracer.member.dto.MemberDTO;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name= "member_table")
public class MemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //unique 제약 조건
    private String memberLoginID;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    private String memberGender;

    private String memberPhoneAgency; //핸드폰 통신사

    private String phone1;
    private String phone2;
    private String phone3;

    private String memberEmail;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();



    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberLoginID(memberDTO.getMemberLoginID());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberGender(memberDTO.getMemberGender());
        memberEntity.setMemberPhoneAgency(memberDTO.getMemberPhoneAgency());
        memberEntity.setPhone1(memberDTO.getPhone1());
        memberEntity.setPhone2(memberDTO.getPhone2());
        memberEntity.setPhone3(memberDTO.getPhone3());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        return memberEntity;
    }
}
