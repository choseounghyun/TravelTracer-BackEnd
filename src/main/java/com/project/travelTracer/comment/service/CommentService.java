package com.project.travelTracer.comment.service;

import com.project.travelTracer.comment.dto.CommentSaveDto;
import com.project.travelTracer.comment.dto.CommentUpdateDto;
import com.project.travelTracer.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    void save(Long postId, CommentSaveDto commentSaveDto);

    void saveRecomment(Long postId, Long parentId, CommentSaveDto commentSaveDto);

    void update(Long id, CommentUpdateDto commentUpdateDto);


    Comment findById(Long id) throws  Exception;

    List<Comment> findAll();

    void remove(Long id) throws Exception;


}
