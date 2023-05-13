package com.project.travelTracer.Post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping("/post")
    public String postWriteForm() {
        return "postWrite";
    }
}
