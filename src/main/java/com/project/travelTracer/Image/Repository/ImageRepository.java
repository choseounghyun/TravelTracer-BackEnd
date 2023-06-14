package com.project.travelTracer.Image.Repository;

import com.project.travelTracer.Image.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByPostId(Long postId);
}
