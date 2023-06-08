package com.project.travelTracer.post.service;


import com.project.travelTracer.Post.dto.PostSaveDto;
import com.project.travelTracer.Post.dto.PostUpdateDto;
import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.Post.service.PostService;
import com.project.travelTracer.member.dto.MemberSignUpDto;
import com.project.travelTracer.member.entity.Role;
import com.project.travelTracer.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PostServiceImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    private static final String userId = "dldpcks34";
    private static final String userPassword = "3251840aa!";

    private void clear() {
        em.flush();
        em.clear();
    }

    private void deleteFile(String filePath) {
        File files = new File(filePath);
        files.delete();
    }

    private MockMultipartFile getMockUploadFile() throws IOException {
        return new MockMultipartFile("file", "file.jpg", "image/jpg", new FileInputStream("/Users/yechan/Desktop/diary.jpg"));
    }

    @BeforeEach
    private void signUpAndSetAuthentication() throws Exception{
        memberService.signUp(new MemberSignUpDto(userId, userPassword, "lee", "dldpcks34@nate.com", 29));
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        User.builder()
                                .username(userId)
                                .password(userPassword)
                                .roles(Role.USER.toString())
                                .build(),
                        null)
        );
        SecurityContextHolder.setContext(emptyContext);
        clear();
    }

    @Test
    public void publishPostNoPicture() throws Exception {
        String title = "제목";
        String content = "내용";
        PostSaveDto postSaveDto = new PostSaveDto(title, content, Optional.empty());

        postService.save(postSaveDto);
        clear();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        Post post = em.find(Post.class, findPost.getId());
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getWriter().getUserId()).isEqualTo(userId);
        assertThat(post.getFilePath()).isNull();
    }

    @Test
    public void publishPostHavingPicture() throws Exception {
        String title = "제목";
        String content = "내용";
        PostSaveDto postSaveDto = new PostSaveDto(title,content, Optional.ofNullable(getMockUploadFile()));

        postService.save(postSaveDto);
        clear();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        Post post = em.find(Post.class, findPost.getId());
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getWriter().getUserId()).isEqualTo(userId);
        assertThat(post.getFilePath()).isNotNull();

        deleteFile(post.getFilePath());

    }

    @Test
    public void failToPublishPost() throws Exception {
        String title = "제목";
        String content = "내용";

        PostSaveDto postSaveDto = new PostSaveDto(null,content, Optional.empty());
        PostSaveDto postSaveDto2 = new PostSaveDto(title,null, Optional.empty());

        assertThrows(Exception.class, ()-> postService.save(postSaveDto));
        assertThrows(Exception.class, ()-> postService.save(postSaveDto2));

    }

    @Test
    public void updatePost() throws Exception {
        String title = "제목";
        String content = "내용";
        PostSaveDto postSaveDto = new PostSaveDto(title,content, Optional.empty());
        postService.save(postSaveDto);
        clear();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        PostUpdateDto postUpdateDto = new PostUpdateDto(Optional.ofNullable("바꾼제목"), Optional.ofNullable("바꾼내용"), Optional.empty());
        postService.update(findPost.getId(), postUpdateDto);
        clear();

        Post post = em.find(Post.class, findPost.getId());
        assertThat(post.getTitle()).isEqualTo("바꾼제목");
        assertThat(post.getWriter().getUserId()).isEqualTo(userId);
        assertThat(post.getFilePath()).isNull();
    }

    @Test
    public void updatePosthHavingPicture() throws Exception {
        String title = "제목";
        String content = "내용";
        PostSaveDto postSaveDto = new PostSaveDto(title,content, Optional.empty());
        postService.save(postSaveDto);
        clear();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        PostUpdateDto postUpdateDto = new PostUpdateDto(Optional.ofNullable("바꾼제목"), Optional.ofNullable("바꾼내용"), Optional.ofNullable(getMockUploadFile()));
        postService.update(findPost.getId(), postUpdateDto);
        clear();

        Post post = em.find(Post.class, findPost.getId());
        assertThat(post.getContent()).isEqualTo("바꾼내용");
        assertThat(post.getWriter().getUserId()).isEqualTo(userId);
        assertThat(post.getFilePath()).isNotNull();

        deleteFile(post.getFilePath());
    }
}