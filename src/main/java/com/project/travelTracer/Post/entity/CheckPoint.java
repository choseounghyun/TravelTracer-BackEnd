package com.project.travelTracer.Post.entity;

import lombok.AccessLevel;
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
}
