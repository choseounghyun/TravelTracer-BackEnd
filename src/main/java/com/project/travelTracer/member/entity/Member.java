package com.project.travelTracer.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "member")
@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id; //primary key

    @Column(nullable = false, length = 30, unique = true)
    private String userId; //아이디

    private String userPassword; //비밀번호

    @Column(nullable = false, length = 30)
    private String userName; //이름

    @Column(nullable = false, length = 30)
    private String userNickName;

    @Column(nullable = false, length=30)
    private Integer age; //나이

    @Enumerated(EnumType.STRING)
    private Role role; //권한 -> USER, ADMIN
}
