package com.project.travelTracer.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    //기본 페이지 요청 메소드
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value="/android", method = {RequestMethod.POST})
    public String androidPage(HttpServletRequest request, Model model){
        System.out.println("서버에서 안드로이드 접속 요청함");
        try{
            String androidID = request.getParameter("id");
            String androidPW = request.getParameter("pw");
            System.out.println("안드로이드에서 받아온 id : " + androidID);
            System.out.println("안드로이드에서 받아온 pw : " + androidPW);
            model.addAttribute("android", androidID);
            return "android";
        }catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }
}
