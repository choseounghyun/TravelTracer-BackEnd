package com.project.travelTracer.Image.Entity;


import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.global.time.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "file")
public class Image extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column
    private String originFileName; //파일 원본명

    @Column
    private String filePath; //파일 경로

    @Column
    private Long fileSize;

    @Builder
    public Image(String originFileName, String filePath, Long fileSize) {
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    //게시물 정보 저장
    public void setPost(Post post) {
        this.post = post;

        if(!post.getImage().contains(this)) {
            post.getImage().add(this);
        }
    }


}
