package com.project.travelTracer.Post.dto;


import com.project.travelTracer.Post.entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostPagingDto {

    private int totalPageCount; // 총 몇페이지인지
    private int currentPageNum; //현재 몇 페이지인지
    private long totalElementCount; //총 게시물 숫자
    private int currentPageElementCount; // 현재 페이지에 존재하는 게시글 수

    private List<BriefPostInfo> simpleLectureDtoList = new ArrayList<>(); //세세한 정보가 아닌 간단게시물 리스트

    public PostPagingDto(Page<Post> searchResult) {
        this.totalPageCount = searchResult.getTotalPages();
        this.currentPageNum = searchResult.getNumber();
        this.totalElementCount = searchResult.getTotalElements();
        this.currentPageElementCount = searchResult.getNumberOfElements();
        this.simpleLectureDtoList = searchResult.getContent().stream().map(BriefPostInfo::new).collect(Collectors.toList());
    }


}
