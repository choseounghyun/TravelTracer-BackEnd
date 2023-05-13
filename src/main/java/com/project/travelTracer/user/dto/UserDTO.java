package com.project.travelTracer.user.dto;

import com.project.travelTracer.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long id;
    private String userLoginID;
    private String userPassword;
    private String userName;
    private String userGender;
    private String userPhoneAgency;
    private String phone1;
    private String phone2;
    private String phone3;
    private String userEmail;

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO= new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserLoginID(user.getUserLoginID());
        userDTO.setUserPassword(user.getUserPassword());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserGender(user.getUserGender());
        userDTO.setUserPhoneAgency(user.getUserPhoneAgency());
        userDTO.setPhone1(userDTO.getPhone1());
        userDTO.setPhone1(userDTO.getPhone2());
        userDTO.setPhone1(userDTO.getPhone3());
        userDTO.setUserEmail(userDTO.getUserEmail());
        return userDTO;
    }
}


