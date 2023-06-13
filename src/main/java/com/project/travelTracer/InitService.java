package com.project.travelTracer;

import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.Post.repository.PostRepository;
import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.comment.repository.CommentRepository;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.lang.String.valueOf;

/**
 *
@RequiredArgsConstructor
@Component
public class InitService {

    private final Init init;

    @PostConstruct
    public void init() {
        init.save();
    }

    @RequiredArgsConstructor
    @Component
    private static class Init {
        private final MemberRepository memberRepository;

        private final PostRepository postRepository;
        private final CommentRepository commentRepository;

        @Transactional
        public void save() {
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

            memberRepository.save(Member.builder().userId("dldpcks345").userPassword(passwordEncoder.encode("325184dd!")).userName("USER1").userEmail("dldpcks34@naver.com").age(29).role(Role.USER).build());
            memberRepository.save(Member.builder().userId("whtmdgus345").userPassword(passwordEncoder.encode("123456aa!")).userName("USER2").userEmail("whtmdgus@naver.com").age(29).role(Role.USER).build());
            memberRepository.save(Member.builder().userId("dlaudtjr345").userPassword(passwordEncoder.encode("09876aa!")).userName("USER3").userEmail("dlaudtjr@naver.com").age(29).role(Role.USER).build());

            Member member = memberRepository.findById(1L).orElse(null);

            for(int i=0; i<=50; i++){
                Post post = Post.builder().title(format("게시글 %s", i)).content(format("내용 %s", i)).address(format("주소 %s",i)).build();
                post.confirmWriter(memberRepository.findById((long)(i%3+1)).orElse(null));
                postRepository.save(post);
            }

            for(int i = 1; i<=150; i++ ){
                Comment comment = Comment.builder().content("댓글" + i).build();
                comment.confirmWriter(memberRepository.findById((long) (i % 3 + 1)).orElse(null));

                comment.confirmPost(postRepository.findById(parseLong(valueOf(i%50 + 1))).orElse(null));
                commentRepository.save(comment);
            }

            commentRepository.findAll().stream().forEach(comment -> {
                for(int i = 1; i<=50; i++ ){
                    Comment recomment = Comment.builder().content("대댓글" + i).build();
                    recomment.confirmWriter(memberRepository.findById((long) (i % 3 + 1)).orElse(null));

                    recomment.confirmPost(comment.getPost());
                    recomment.confirmParent(comment);
                    commentRepository.save(recomment);
                }
            });


        }
    }
}
**/