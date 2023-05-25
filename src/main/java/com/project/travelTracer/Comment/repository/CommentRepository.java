package com.project.travelTracer.Comment.repository;

import com.project.travelTracer.Comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
