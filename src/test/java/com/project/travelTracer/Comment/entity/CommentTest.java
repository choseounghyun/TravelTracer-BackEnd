package com.project.travelTracer.Comment.entity;

import com.project.travelTracer.Comment.repository.CommentRepository;
import com.project.travelTracer.Comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager em;

    private void clear() {
        em.flush();
        em.clear();
    }

    private Long saveComment() {
        Comment comment = Comment.builder().content("댓글").build();
        Long id = commentRepository.save(comment).getId();
        clear();
        return id;
    }

    private Long saveRecomment(Long parentId) {
        Comment parent = commentRepository.findById(parentId).orElse(null);
        Comment comment = Comment.builder().content("댓글").parent(parent).build();

        Long id = commentRepository.save(comment).getId();
        return id;
    }
}