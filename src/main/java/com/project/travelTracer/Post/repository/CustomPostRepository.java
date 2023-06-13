package com.project.travelTracer.Post.repository;

import com.project.travelTracer.Post.condition.PostSearchCondition;
import com.project.travelTracer.Post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {

    Page<Post> search(PostSearchCondition postSearchCondition, Pageable pageable);
}
