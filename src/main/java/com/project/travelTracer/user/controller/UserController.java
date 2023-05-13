package com.project.travelTracer.user.controller;

import com.project.travelTracer.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final com.project.travelTracer.user.service.UserService UserService;

    @GetMapping("/user/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/user/save")
    public String save(@ModelAttribute UserDTO UserDTO) {
        System.out.println("UserController.save");
        System.out.println("UserDTO = " + UserDTO);
        UserService.save(UserDTO);
        return "login";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDTO UserDTO, HttpSession session) {
        com.project.travelTracer.user.dto.UserDTO loginResult = UserService.login(UserDTO);
        if(loginResult != null) {
            //로그인 성공
            //로그인 상태에서 회원정보가 유지되기 위해 세션값을 제공
            session.setAttribute("loginID", loginResult.getUserLoginID());
            return "main";
        }
        else {
            //실패
            return "login";
        }
    }

}
