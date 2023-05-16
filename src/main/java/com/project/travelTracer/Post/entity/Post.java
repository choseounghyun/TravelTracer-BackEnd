package com.project.travelTracer.Post.entity;

import com.project.travelTracer.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "POST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Column(length = 40, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String filePath;



    public void confirmWriter(Member writer) {
        this.writer = writer;
        writer.addPost(this);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateFilePath(String filePath) {
        this.filePath = filePath;
    }

}
