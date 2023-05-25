package com.project.travelTracer.Comment.service;

import com.project.travelTracer.Comment.entity.Comment;

import java.util.List;

public interface CommentService {

    void save(Comment comment);

    Comment findById(Long id) throws  Exception;

    List<Comment> findAll();

    void remove(Long id) throws Exception;


}
