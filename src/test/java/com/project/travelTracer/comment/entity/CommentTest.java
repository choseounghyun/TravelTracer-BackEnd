package com.project.travelTracer.comment.entity;

import com.project.travelTracer.Post.dto.PostSaveDto;
import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.Post.exception.PostException;
import com.project.travelTracer.Post.exception.PostExceptionType;
import com.project.travelTracer.Post.repository.PostRepository;
import com.project.travelTracer.comment.dto.CommentSaveDto;
import com.project.travelTracer.comment.repository.CommentRepository;
import com.project.travelTracer.comment.service.CommentService;
import com.project.travelTracer.member.dto.MemberSignUpDto;
import com.project.travelTracer.member.entity.Role;
import com.project.travelTracer.member.repository.MemberRepository;
import com.project.travelTracer.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class CommentTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    private void clear() {
        em.flush();
        em.clear();
    }

    @BeforeEach
    private void signUpAndSetAuthentication() throws Exception {
        memberService.signUp(new MemberSignUpDto("USER1", "3251840aa!", "lee", "dldpcks34@nate.com", 29));
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        User.builder()
                                .username("USER1")
                                .password("3251840aa!")
                                .roles(Role.USER.toString())
                                .build(),
                        null)
        );
        SecurityContextHolder.setContext(emptyContext);
        clear();
    }

    private void anotherSignUpAndSetAuthentication() throws Exception {
        memberService.signUp(new MemberSignUpDto("USER2","325184dd!","name","nickName",22));
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        User.builder()
                                .username("USER2")
                                .password("325184dd!")
                                .roles(Role.USER.toString())
                                .build(),
                        null)
        );
        SecurityContextHolder.setContext(emptyContext);
        clear();
    }

    private Long savePost() {
        String title = "제목";
        String content = "내용";
        String address = "서울시";


        PostSaveDto postSaveDto = new PostSaveDto(title, content, address, Optional.empty());
        Post save = postRepository.save(postSaveDto.toEntity());
        clear();
        return save.getId();
    }

    private Long saveComment() {
        CommentSaveDto commentSaveDto = new CommentSaveDto("댓글");
        commentService.save(savePost(), commentSaveDto);
        log.info(String.valueOf(savePost()));
        clear();

        List<Comment> resultList = em.createQuery("select c from Comment c order by c.createdDate desc", Comment.class).getResultList();
        return resultList.get(0).getId();
    }

    private Long saveRecomment(Long parentId) {
        CommentSaveDto commentSaveDto = new CommentSaveDto("대댓글");
        commentService.saveRecomment(savePost(), parentId, commentSaveDto);
        clear();

        List<Comment> resultList = em.createQuery("select c from Comment c order by c.createdDate desc", Comment.class).getResultList();
        return resultList.get(0).getId();

    }

    @Test
    public void commentSave_Success() throws  Exception {
        Long postId = savePost();
        CommentSaveDto commentSaveDto = new CommentSaveDto("댓글");

        commentService.save(postId, commentSaveDto);
        clear();

        List<Comment> resultList = em.createQuery("select c from Comment c order by c.createdDate desc", Comment.class).getResultList();
        assertThat(resultList.size()).isEqualTo(1);
    }

    @Test
    public void recommentSave_success() throws Exception {
        Long postId = savePost();
        Long parentId = saveComment();
        CommentSaveDto commentSaveDto = new CommentSaveDto("대댓글");

        commentService.saveRecomment(postId, parentId, commentSaveDto);
        clear();

        List<Comment> resultList = em.createQuery("select c from Comment c order by c.createdDate desc", Comment.class).getResultList();
        assertThat(resultList.size()).isEqualTo(2);
    }

    @Test
    public void commentSave_fail() throws Exception {
        Long postId = savePost();
        CommentSaveDto commentSaveDto = new CommentSaveDto("댓글");

        assertThat(assertThrows(PostException.class, () -> commentService.save(postId+1, commentSaveDto)));

    }

    @Test
    public void recommentSave_fail() throws Exception {
        Long postId = savePost();
        Long parentId = saveComment();
        CommentSaveDto commentSaveDto = new CommentSaveDto("댓글");

        assertThat(assertThrows(PostException.class, () -> commentService.saveRecomment(postId+123, parentId, commentSaveDto))
                .getExceptionType()).isEqualTo(PostExceptionType.POST_NOT_POUND);
    }


}