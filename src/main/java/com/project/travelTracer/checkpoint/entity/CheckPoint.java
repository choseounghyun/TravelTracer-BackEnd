package com.project.travelTracer.checkpoint.entity;

import com.project.travelTracer.Post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CHECKPOINT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckPoint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkpoint_id")
    private Long id;

    @OneToOne(mappedBy = "checkpoint")
    private Post post;

    //위도
    @Column(length = 40, nullable = false)
    private String latitude;

    //경도
    @Column(length = 40, nullable = false)
    private String longitude;

    @Builder
    public CheckPoint(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
