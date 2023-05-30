package com.project.travelTracer.comment.entity;

import com.project.travelTracer.comment.repository.CommentRepository;
import com.project.travelTracer.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
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
        clear();
        return id;
    }

    @Test
    public void deleteParentCommentHavingChild() throws  Exception {
        Long commendId = saveComment();
        saveRecomment(commendId);
        saveRecomment(commendId);
        saveRecomment(commendId);
        saveRecomment(commendId);

        assertThat(commentService.findById(commendId).getChildList().size()).isEqualTo(4);

        commentService.remove(commendId);
        clear();

        Comment findCommentId = commentService.findById(commendId);
        assertThat(findCommentId).isNotNull();
        assertThat(findCommentId.isRemoved()).isTrue();
        assertThat(findCommentId.getChildList().size()).isEqualTo(4);
        log.info(findCommentId.getContent());
    }

    //댓글을 삭제 할 때 대댓글이 없음 바로 DB에서 삭제
    @Test
    public void deleteParentComment() throws Exception {
        Long commentId = saveComment();

        commentService.remove(commentId);

        assertThat(commentService.findAll().size()).isSameAs(0);

    }


}