package com.project.travelTracer.Post.entity;

import com.project.travelTracer.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name= "post_table")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String address;

    private String title;

    private Category category;

    private String content;

    private String tag; //태그는 어떻게 데이터를 넣어야할지 고민


}
