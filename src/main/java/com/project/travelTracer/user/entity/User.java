package com.project.travelTracer.user.entity;

import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.user.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name= "user_table")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(unique = true) //unique 제약 조건
    private String userLoginID;

    @Column
    private String userPassword;

    @Column
    private String userName;

    private String userGender;

    private String userPhoneAgency; //핸드폰 통신사

    private String phone1;
    private String phone2;
    private String phone3;

    private String userEmail;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public static User toUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserLoginID(userDTO.getUserLoginID());
        user.setUserName(userDTO.getUserName());
        user.setUserPassword(userDTO.getUserPassword());
        user.setUserGender(userDTO.getUserGender());
        user.setUserPhoneAgency(userDTO.getUserPhoneAgency());
        user.setPhone1(userDTO.getPhone1());
        user.setPhone2(userDTO.getPhone2());
        user.setPhone3(userDTO.getPhone3());
        user.setUserEmail(userDTO.getUserEmail());
        return user;
    }
}
