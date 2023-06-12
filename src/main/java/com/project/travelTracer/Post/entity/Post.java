package com.project.travelTracer.Post.entity;

import com.project.travelTracer.checkpoint.entity.CheckPoint;
import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.global.time.BaseTimeEntity;
import com.project.travelTracer.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "POST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Column(length = 40, nullable = false)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkpoint_id")
    private CheckPoint checkpoint;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String filePath;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //== 게시글을 삭제하면 달려있는 댓글 모두 삭제 ==//
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void confirmWriter(Member writer) {
        this.writer = writer;
        writer.addPost(this);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void addComment(Comment comment){
        //comment의 Post 설정은 comment에서 함
        commentList.add(comment);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCheckpoint(CheckPoint checkpoint) {
        this.checkpoint = checkpoint;
    }
}
