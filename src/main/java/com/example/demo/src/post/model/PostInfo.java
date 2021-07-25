package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostInfo {

    private String userProfileImgUrl;
    private String userAccountId;
    private String postContents;
    private String createdAt;
}
