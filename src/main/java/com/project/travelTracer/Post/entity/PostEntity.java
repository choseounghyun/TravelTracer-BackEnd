package com.project.travelTracer.Post.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name= "post_table")
public class PostEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String title;

    private Category category;

    private String content;

    private String tag; //태그는 어떻게 데이터를 넣어야할지 고민


}
