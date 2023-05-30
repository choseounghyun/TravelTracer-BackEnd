package com.project.travelTracer.Post.repository;

import com.project.travelTracer.Post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
