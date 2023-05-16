package com.project.travelTracer.member.entity;

import com.project.travelTracer.BaseTimeEntity;
import com.project.travelTracer.Post.entity.Comment;
import com.project.travelTracer.Post.entity.Post;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id; //primary key

    @Column(nullable = false, length = 30, unique = true)
    private String userId; //아이디

    private String userPassword; //비밀번호

    @Column(nullable = false, length = 30)
    private String userName; //이름

    @Column(nullable = false, length=30)
    private Integer age; //나이

    @Enumerated(EnumType.STRING)
    private Role role; //권한 -> USER, ADMIN

    @Column(length = 1000)
    private String refreshToken;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    public void addPost(Post post){
        //post의 writer 설정은 post에서 함
        postList.add(post);
    }

    public void addComment(Comment comment){
        //comment의 writer 설정은 comment에서 함
        commentList.add(comment);
    }


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

    //== 패스워드 암호화 ==//
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(userPassword);
    }

    public void updateName(String userName){
        this.userName = userName;
    }

    public void updateAge(int age){
        this.age = age;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String userPassword) {
        this.userPassword = passwordEncoder.encode(userPassword);
    }


    //비밀번호 변경, 회원 정보 수정,삭제 시 비밀번호를 확인하기 위한 메서드
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getUserPassword());
    }

    public void addUserAuthority() {
        this.role = Role.USER;
    }
}
