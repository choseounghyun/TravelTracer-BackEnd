package com.project.travelTracer;

import com.project.travelTracer.Post.repository.PostRepository;
import com.project.travelTracer.comment.repository.CommentRepository;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class InitService {

    private final Init init;

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

            memberRepository.save(Member.builder().userName("dldpcks345").userPassword("325184dd!").userName("USER1").userEmail("dldpcks34@naver.com").age(29).role(Role.USER).build());
            memberRepository.save(Member.builder().userName("whtmdgus345").userPassword("123456aa!").userName("USER2").userEmail("whtmdgus@naver.com").age(29).role(Role.USER).build());
            memberRepository.save(Member.builder().userName("dlaudtjr345").userPassword("09876aa!").userName("USER3").userEmail("dlaudtjr@naver.com").age(29).role(Role.USER).build());

            Member member = memberRepository.findById(1L).orElse(null);

            for(int i=0; i<=50; i++){

            }


        }
    }
}
