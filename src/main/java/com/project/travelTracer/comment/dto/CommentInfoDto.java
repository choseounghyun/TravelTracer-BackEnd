package com.project.travelTracer.comment.dto;

import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.member.dto.MemberInfoDto;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class CommentInfoDto {

    private final static String DELETE_MESSAGE = "삭제된 댓글입니다.";

    private Long postId; //댓글달린 post 아이디

    private Long commentId; //해당 댓글의 아이디
    private String content;
    private boolean isRemoved;

    private MemberInfoDto writerDto;

    @Nullable
    private List<ReCommentInfoDto> reCommentInfoDtoList;

    public CommentInfoDto(Comment comment, @Nullable List<Comment> reCommentList) {
        this.postId = comment.getPost().getId();
        this.commentId = comment.getId();

        this.content = comment.getContent();
        if(comment.isRemoved()) {
            this.content = DELETE_MESSAGE;
        }
        this.isRemoved = comment.isRemoved();

        this.writerDto = new MemberInfoDto(comment.getWriter());

        this.reCommentInfoDtoList = reCommentList.stream().
                map(ReCommentInfoDto::new).collect(Collectors.toList());

    }
}
