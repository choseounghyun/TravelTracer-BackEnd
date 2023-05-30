package com.project.travelTracer.comment.repository;

import com.project.travelTracer.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
