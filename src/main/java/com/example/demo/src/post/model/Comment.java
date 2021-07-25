package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Comment {

    private int idx;
    private String accountId;
    private String profileImgUrl;
    private int likeCnt;
    private String contents;
    private int recommentIdx;
    private String updatedAt;

}
