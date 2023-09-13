package com.project.travelTracer.location.entity;

import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Table(name = "checkpoint")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class CheckPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkpoint_id")
    private long id;

    @Column(nullable = false, unique = true)
    private int locationId;

    private String locationName;

    //위도
    @Column(length = 40, nullable = false)
    private double latitude;

    //경도
    @Column(length = 40, nullable = false)
    private double longitude;

    @CreatedDate
    private long createtime;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
    @Enumerated(EnumType.STRING)
    private Role role; //권한 -> USER, ADMIN


    public void addUserAuthority() {
        this.role = Role.USER;
    }

}
