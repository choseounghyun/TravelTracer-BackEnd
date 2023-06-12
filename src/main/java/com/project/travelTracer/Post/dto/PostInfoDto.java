package com.project.travelTracer.Post.dto;

import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.comment.dto.CommentInfoDto;
import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.member.dto.MemberInfoDto;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class PostInfoDto {

    private Long postId; //post의 id

    private String title; //제목

    private String content; //내용

    private String filePath; //업로드 파일 경로

    private MemberInfoDto writerDto; //작성자에 대한 정보

    private List<CommentInfoDto> commentInfoDtoList; //댓글 정보들

    public PostInfoDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.filePath = post.getFilePath();

        this.writerDto = new MemberInfoDto(post.getWriter());

        /**
         * 댓ㄱㄹ과 대댓글을 그룹짓기
         * post.getCommentList()는 댓글과 대댓글이 모두 조회된다.
         */
        Map<Comment, List<Comment>> commentListMap = post.getCommentList().stream()
                .filter(comment -> comment.getParent() != null)
                .collect(Collectors.groupingBy(Comment::getParent));

        //대댓글이 없는 댓글
        commentInfoDtoList.addAll(post.getCommentList().stream()
                .filter(comment -> comment.getParent() == null)
                .map(comment -> new CommentInfoDto(comment, null))
                .collect(Collectors.toList()));

        /**
         * 댓글과 대댓글을 통해 CommentInfoDto 생성
         */
        commentInfoDtoList.addAll(commentListMap.keySet().stream()
                .map(comment -> new CommentInfoDto(comment, commentListMap.get(comment)))
                .collect(Collectors.toList()));


    }
}
