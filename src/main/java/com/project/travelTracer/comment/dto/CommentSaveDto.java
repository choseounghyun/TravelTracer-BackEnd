package com.project.travelTracer.comment.dto;

import com.project.travelTracer.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveDto {

    @NotBlank(message = "내용을 입력해주세요")
    String content;

    public Comment toEntity() {
        return Comment.builder().content(content).build();
    }
}
