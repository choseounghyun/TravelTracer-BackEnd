/**
 *

package com.project.travelTracer.post.service;


import com.project.travelTracer.Post.condition.PostSearchCondition;
import com.project.travelTracer.Post.dto.PostInfoDto;
import com.project.travelTracer.Post.dto.PostPagingDto;
import com.project.travelTracer.Post.dto.PostSaveDto;
import com.project.travelTracer.Post.dto.PostUpdateDto;
import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.Post.repository.PostRepository;
import com.project.travelTracer.Post.service.PostService;
import com.project.travelTracer.comment.dto.CommentInfoDto;
import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.comment.repository.CommentRepository;
import com.project.travelTracer.member.dto.MemberSignUpDto;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import com.project.travelTracer.member.repository.MemberRepository;
import com.project.travelTracer.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.print.attribute.standard.PageRanges;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private static final String userId = "dldpcks345";
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
        memberService.signUp(new MemberSignUpDto(userId, userPassword, "lee", "dldpcks34@google.com", 29));
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
        String address ="서울시";
        PostSaveDto postSaveDto = new PostSaveDto(title, content,address, Optional.empty());

        postService.save(postSaveDto);
        clear();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        Post post = em.find(Post.class, findPost.getId());
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getWriter().getUserId()).isEqualTo(userId);
    }

    @Test
    public void publishPostHavingPicture() throws Exception {
        String title = "제목";
        String content = "내용";
        String address ="서울시";

        PostSaveDto postSaveDto = new PostSaveDto(title,content, address,Optional.ofNullable(getMockUploadFile()));

        postService.save(postSaveDto);
        clear();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        Post post = em.find(Post.class, findPost.getId());
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getWriter().getUserId()).isEqualTo(userId);


    }

    @Test
    public void failToPublishPost() throws Exception {
        String title = "제목";
        String content = "내용";
        String address ="서울시";

        PostSaveDto postSaveDto = new PostSaveDto(null,content, address,Optional.empty());
        PostSaveDto postSaveDto2 = new PostSaveDto(title,null, address,Optional.empty());

        assertThrows(Exception.class, ()-> postService.save(postSaveDto));
        assertThrows(Exception.class, ()-> postService.save(postSaveDto2));

    }

    @Test
    public void updatePost() throws Exception {
        String title = "제목";
        String content = "내용";
        String address ="서울시";

        PostSaveDto postSaveDto = new PostSaveDto(title,content,address, Optional.empty());
        postService.save(postSaveDto);
        clear();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        PostUpdateDto postUpdateDto = new PostUpdateDto(Optional.ofNullable("바꾼제목"), Optional.ofNullable("바꾼내용"), Optional.ofNullable("바꾼 주소 "),Optional.empty());
        postService.update(findPost.getId(), postUpdateDto);
        clear();

        Post post = em.find(Post.class, findPost.getId());
        assertThat(post.getTitle()).isEqualTo("바꾼제목");
        assertThat(post.getWriter().getUserId()).isEqualTo(userId);
    }

    @Test
    public void updatePostHavingPicture() throws Exception {
        String title = "제목";
        String content = "내용";
        String address ="서울시;";

        PostSaveDto postSaveDto = new PostSaveDto(title,content,address, Optional.empty());
        postService.save(postSaveDto);
        clear();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        PostUpdateDto postUpdateDto = new PostUpdateDto(Optional.ofNullable("바꾼제목"), Optional.ofNullable("바꾼내용"), Optional.ofNullable("바꾼 주소 "),Optional.ofNullable(getMockUploadFile()));
        postService.update(findPost.getId(), postUpdateDto);
        clear();

        Post post = em.find(Post.class, findPost.getId());
        assertThat(post.getContent()).isEqualTo("바꾼내용");
        assertThat(post.getWriter().getUserId()).isEqualTo(userId);

    }

    @Test
    public void updatePostNonHavingPicture() throws Exception {
        String title= "제목";
        String content = "내용";
        String address ="서울시";
        PostSaveDto postSaveDto = new PostSaveDto(title, content, address,Optional.ofNullable(getMockUploadFile()));
        postService.save(postSaveDto);

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();
        assertThat(findPost.getFilePath()).isNotNull();
        clear();

        PostUpdateDto postUpdateDto = new PostUpdateDto(Optional.ofNullable("바꾼 제목"), Optional.ofNullable("바꾼내용"),Optional.ofNullable("바꾼 주소 "), Optional.empty());
        postService.update(findPost.getId(), postUpdateDto);

        findPost = em.find(Post.class, findPost.getId());
        assertThat(findPost.getContent()).isEqualTo("바꾼내용");
        assertThat(findPost.getWriter().getUserId()).isEqualTo(userId);
        assertThat(findPost.getFilePath()).isNull();

    }

    @Test
    public void post_check() throws Exception {
        Member member1 = memberRepository.save(Member.builder().userId("dldpcks111").userPassword("325184ddd!").userName("USER1").userEmail("dldpcks34@nate.com").age(29).role(Role.USER).build());
        Member member2 = memberRepository.save(Member.builder().userId("whtmdgus345").userPassword("123456aa!").userName("USER2").userEmail("whtmdgus@naver.com").age(29).role(Role.USER).build());
        Member member3 = memberRepository.save(Member.builder().userId("dlaudtjr345").userPassword("09876aa!").userName("USER3").userEmail("dlaudtjr@naver.com").age(29).role(Role.USER).build());
        Member member4 = memberRepository.save(Member.builder().userId("dlaudt345").userPassword("09876aa!!").userName("USER4").userEmail("dlaudtj22r@naver.com").age(29).role(Role.USER).build());
        Member member5 = memberRepository.save(Member.builder().userId("dlaur345").userPassword("09876aaccc!").userName("USER5").userEmail("dsadas@naver.com").age(29).role(Role.USER).build());

        Map<Integer, Long> memberIdMap = new HashMap<>();
        memberIdMap.put(1, member1.getId());
        memberIdMap.put(2, member2.getId());
        memberIdMap.put(3, member3.getId());
        memberIdMap.put(4, member5.getId());
        memberIdMap.put(5, member2.getId());

        Post post = Post.builder().title("게시글").content("내용").address("서울시").build();
        post.confirmWriter(member1);
        postRepository.save(post);
        em.flush();


        final int COMMENT_COUNT= 10;
        for(int i=1; i<=10; i++) {
            Comment comment = Comment.builder().content("댓글" + i).build();
            comment.confirmWriter(memberRepository.findById(memberIdMap.get(i%5+1)).orElse(null));
            comment.confirmPost(post);
            commentRepository.save(comment);
        }

        final int COMMENT_PER_RECOMMENT_COUNT = 20;
        commentRepository.findAll().stream().forEach(comment -> {
            for(int i = 1; i<=20; i++ ){
                Comment recomment = Comment.builder().content("대댓글" + i).build();
                recomment.confirmWriter(memberRepository.findById(memberIdMap.get(i % 5 + 1)).orElse(null));

                recomment.confirmPost(comment.getPost());
                recomment.confirmParent(comment);
                commentRepository.save(recomment);
            }
        });
        Comment NoreComment = Comment.builder().content("대댓글없는 댓글").build();
        NoreComment.confirmWriter(member3);
        NoreComment.confirmPost(post);
        commentRepository.save(NoreComment);

        clear();

        PostInfoDto postInfoDto = postService.getPostInfo(post.getId());

        assertThat(postInfoDto.getPostId()).isEqualTo(post.getId());
        int recommentCount = 0;
        for(CommentInfoDto commentInfoDto : postInfoDto.getCommentInfoDtoList()) {
            recommentCount += commentInfoDto.getReCommentInfoDtoList().size();
        }
        assertThat(postInfoDto.getCommentInfoDtoList().size()).isEqualTo(11);
        assertThat(recommentCount).isEqualTo(COMMENT_PER_RECOMMENT_COUNT * COMMENT_COUNT);
    }

    @Test
    public void post_search_noCondition() throws Exception {
        //given
        Member member1 = memberRepository.save(Member.builder().userId("dldpcks111").userPassword("325184ddd!").userName("USER1").userEmail("dldpcks34@nate.com").age(29).role(Role.USER).build());

        final int POST_COUNT = 50;
        for(int i=1; i<=POST_COUNT; i++) {
            Post post = Post.builder().title("게시글" + i).content("내용" + i).address("서울시" + i).build();
            post.confirmWriter(member1);
            postRepository.save(post);
        }
        clear();

        //when
        final int PAGE = 0;
        final int SIZE = 20;

        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);
        PostSearchCondition postSearchCondition = new PostSearchCondition();

        PostPagingDto postList = postService.getPostList(pageRequest, postSearchCondition);


        //then
        assertThat(postList.getTotalElementCount()).isEqualTo(POST_COUNT);
        assertThat(postList.getTotalPageCount())
                .isEqualTo((POST_COUNT % SIZE ==0) ? POST_COUNT/SIZE : POST_COUNT/SIZE+1);
        assertThat(postList.getCurrentPageNum()).isEqualTo(PAGE);
        assertThat(postList.getCurrentPageElementCount()).isEqualTo(SIZE);
    }

    @Test
    public void post_search_titleCondition() throws Exception {

        //given
        Member member1 = memberRepository.save(Member.builder().userId("dldpcks111").userPassword("325184ddd!").userName("USER1").userEmail("dldpcks34@nate.com").age(29).role(Role.USER).build());

        final int DEFAULT_POST_COUNT = 100;
        for(int i = 1; i<=DEFAULT_POST_COUNT; i++ ){
            Post post = Post.builder().title("게시글" + i).content("내용" + i).address("서울시" + i).build();
            post.confirmWriter(member1);
            postRepository.save(post);
        }

        final String SEARCH_TITLE_STR = "AAA";
        final int COND_POST_COUNT = 100;
        for(int i = 1; i<=COND_POST_COUNT; i++ ){
            Post post = Post.builder().title(SEARCH_TITLE_STR+ i).content("내용"+i).address("서울시" + i).build();
            post.confirmWriter(member1);
            postRepository.save(post);
        }
        clear();

        //when
        final int PAGE = 2;
        final int SIZE = 20;
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);

        PostSearchCondition postSearchCondition = new PostSearchCondition();
        postSearchCondition.setTitle(SEARCH_TITLE_STR);

        PostPagingDto postList = postService.getPostList(pageRequest, postSearchCondition);

        assertThat(postList.getTotalElementCount()).isEqualTo(COND_POST_COUNT);
        assertThat(postList.getTotalPageCount())
                .isEqualTo((COND_POST_COUNT % SIZE == 0) ? COND_POST_COUNT/SIZE : COND_POST_COUNT/SIZE + 1);
        assertThat(postList.getCurrentPageNum()).isEqualTo(PAGE);
        assertThat(postList.getCurrentPageElementCount()).isEqualTo(SIZE);
    }

    @Test
    public void post_search_contentCondition() throws Exception {

        //given
        Member member1 = memberRepository.save(Member.builder().userId("dldpcks111").userPassword("325184ddd!").userName("USER1").userEmail("dldpcks34@nate.com").age(29).role(Role.USER).build());

        final int DEFAULT_POST_COUNT = 100;
        for(int i = 1; i<=DEFAULT_POST_COUNT; i++ ){
            Post post = Post.builder().title("게시글" + i).content("내용" + i).address("서울시" + i).build();
            post.confirmWriter(member1);
            postRepository.save(post);
        }

        final String SEARCH_CONTENT_STR = "AAA";
        final int COND_POST_COUNT = 100;
        for(int i = 1; i<=COND_POST_COUNT; i++ ){
            Post post = Post.builder().title("타이틀" + i).content(SEARCH_CONTENT_STR + i).address("서울시" + i).build();
            post.confirmWriter(member1);
            postRepository.save(post);
        }
        clear();

        final int PAGE = 2;
        final int SIZE = 20;
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);

        PostSearchCondition postSearchCondition = new PostSearchCondition();
        postSearchCondition.setContent(SEARCH_CONTENT_STR);

        PostPagingDto postList = postService.getPostList(pageRequest, postSearchCondition);

        assertThat(postList.getTotalElementCount()).isEqualTo(COND_POST_COUNT);
        assertThat(postList.getTotalPageCount())
                .isEqualTo((COND_POST_COUNT % SIZE == 0) ? COND_POST_COUNT/SIZE : COND_POST_COUNT/SIZE + 1);
        assertThat(postList.getCurrentPageNum()).isEqualTo(PAGE);
        assertThat(postList.getCurrentPageElementCount()).isEqualTo(SIZE);
    }


}
 **/