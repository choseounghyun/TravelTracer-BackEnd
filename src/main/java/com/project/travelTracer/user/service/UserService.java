package com.project.travelTracer.user.service;

import com.project.travelTracer.user.dto.UserDTO;
import com.project.travelTracer.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final com.project.travelTracer.user.repository.UserRepository UserRepository;

    public void save(UserDTO UserDTO) {
        User user = User.toUserEntity(UserDTO);
        UserRepository.save(user);
        //중요한 건, 리포지토리에 저장하려면 엔티티객체를 넘겨야 한다 DTO X
    }
    public UserDTO login(UserDTO UserDTO) {
        Optional<User> UserLoginID = UserRepository.findByUserLoginID(UserDTO.getUserLoginID());
        if(UserLoginID.isPresent()) {
            //isPresent() 는 조회 결과가 있는지(null 값이 아닌지)
            User user = UserLoginID.get();
            //들어온 이메일을 가진 정보의 비밀번호와 DB의 비밀번호가 일치하는 지 확인
            if(user.getUserPassword().equals(UserDTO.getUserPassword())) {
                //entity -> DTO로 변환
                return UserDTO.toUserDTO(user);
            }
            else {
                //비밀번호 불일치
                return null;
            }
        }
        else {
            //없는 이메일일때
            return null;
        }
    }


}
