package com.project.travelTracer.comment.service;

import com.project.travelTracer.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    void save(Comment comment);

    Comment findById(Long id) throws  Exception;

    List<Comment> findAll();

    void remove(Long id) throws Exception;


}
