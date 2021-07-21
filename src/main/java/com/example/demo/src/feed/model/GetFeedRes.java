package com.example.demo.src.feed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetFeedRes {

    private String userProfileImgUrl;
    private int userAccountId;
    private String userDescription;

    private int postCnt;
    private int followerCnt;
    private int followingCnt;

    private List<String> storyGroup;
    private List<String> post;
}
