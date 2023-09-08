package com.project.travelTracer.location.entity;

import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    private LocalDateTime createtime;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

}
