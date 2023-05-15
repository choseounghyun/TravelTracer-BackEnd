package com.project.travelTracer.Post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    @GetMapping("/post/write")
    public String postWriteForm() {
        return "postWrite";
    }

    @PostMapping("/post/writeProcess")
    public String boardWriteProcess(){

        return "";
    }
}
