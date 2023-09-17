package com.project.travelTracer.member.entity;

import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.global.time.BaseTimeEntity;
import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.location.entity.CheckPoint;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//db로 저장하는 부분
@Table(name = "member") // 엔티티와 매핑될 데이터베이스 테이블
@Getter //메스드 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //파라미터 없는 생성자를 자동으로 생성, protected 접근 제어를 가지므로 외부에서 직접 생성할 수 없음
@Entity
@AllArgsConstructor  //모든 필드를 인자로 받는 생성자를 자동으로 생성
@Builder  //빌더 패턴을 적용한 빌더 클래스를 자동으로 생성
public class Member extends BaseTimeEntity {

    @Id // 기본 키 필드임을 표시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 자동 생성
    @Column(name="member_id") // 엔티티 필드와 DB 테이블 컬럼 매핑
    private Long id; //primary key

    @Column(nullable = false, length = 30, unique = true)
    private String userId; //아이디

    private String userPassword; //비밀번호

    @Column(nullable = false, length = 30)
    private String userName; //이름

    @Column(nullable = false, length=30)
    private Integer age; //나이


    @Column(nullable = false, unique = true)
    private String userEmail;//이메일

    @Enumerated(EnumType.STRING)
    private Role role; //권한 -> USER, ADMIN

    @Column(length = 1000)
    private String refreshToken;

    @Builder.Default
    @OneToMany(mappedBy="member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckPoint> checkpointList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @Builder.Default
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

    public void updateEmail(String userEmail) {
        this.userEmail = userEmail;
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
