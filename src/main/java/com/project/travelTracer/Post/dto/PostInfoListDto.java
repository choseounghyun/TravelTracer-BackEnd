package com.project.travelTracer.Post.dto;

import com.project.travelTracer.Post.entity.Post;
import lombok.Getter;

@Getter
public class PostInfoListDto {

    private Long id;
    private String title;
    private Long thumnailId;

    public PostInfoListDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();

        if(!post.getImage().isEmpty()) {
            this.thumnailId = post.getImage().get(0).getId();
        }
        else {
            this.thumnailId = 0L; //서버에 저장된 기본 이미지 변환
        }
    }
}
