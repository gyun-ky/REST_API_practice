package com.example.demo.src.feed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Story {

    private int idx;
    private String profileImgUrl;
    private String accountId;
    private String createdAt;

}
