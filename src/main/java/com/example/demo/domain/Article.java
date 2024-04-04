package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class Article {
    private Long id; // id
    private String title; // 제목
    private String content; // 내용
    private String hashTag; // 해시태그

    private LocalDateTime createdAt; // 생성일시
    private String createdBy; // 생성자
    private LocalDateTime modifiedAt; // 수정일시
    private String modifiedBy; // 수정자
}
