package com.project.travelTracer.member.dto;

import com.project.travelTracer.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Getter
@Setter
public class MemberSignUpDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 7, max = 25, message = "아이디는 7~25자 내외로 입력해주세요")
    String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    String userPassword;

    @NotBlank(message = "이름을 입력해주세요") @Size(min=2, message = "사용자 이름이 너무 짧습니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해주세요.")
    String userName;

    @NotNull(message = "나이를 입력해주세요")
    @Range(min = 0, max = 150)
    Integer age;

    @Email
    String userEmail;

    public MemberSignUpDto(String userId, String userPassword, String userName,String userEmail, Integer age) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.age = age;
        this.userEmail = userEmail;
    }

    public MemberSignUpDto() {

    }

    public Member toEntity() {
        return Member.builder().userId(userId)
                .userPassword(userPassword)
                .userEmail(userEmail)
                .userName(userName)
                .age(age)
                .build();
    }
}
