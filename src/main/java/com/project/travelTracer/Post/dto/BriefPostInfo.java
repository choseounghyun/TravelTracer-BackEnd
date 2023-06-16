package com.project.travelTracer.Post.dto;

import com.project.travelTracer.Post.entity.Category;
import com.project.travelTracer.Post.entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BriefPostInfo {

    private Long postId;

    private String title;//제목
    private String content;//내용
    private String address; //주소
    private Category category; //카테고리
    private String writerName;//작성자의 이름
    private String createdDate; //작성일

    public BriefPostInfo(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.address = post.getAddress();
        this.writerName = post.getWriter().getUserName();
        this.createdDate = post.getCreatedDate().toString();
    }
}
