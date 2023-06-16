package com.project.travelTracer.Post.condition;

import com.project.travelTracer.Post.entity.Category;
import lombok.Data;

@Data
public class PostSearchCondition {

    private String title;
    private String content;
    private String address;
    private Category category;
}
