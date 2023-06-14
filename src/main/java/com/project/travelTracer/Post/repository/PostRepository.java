package com.project.travelTracer.Post.repository;

import com.project.travelTracer.Post.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> , CustomPostRepository{

    @EntityGraph(attributePaths = {"writer"})
    Optional<Post> findWithWriterById(Long id);

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();
}
