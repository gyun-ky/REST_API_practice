package com.example.demo.src.accountHome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetAccountHomeRes {

    private String userProfileImgUrl;
    private String userAccountId;
    private String userAccountName;
    private String userDescription;
    private String description;

    private int postCnt;
    private int followerCnt;
    private int followingCnt;

    private List<StoryGroup> storyGroup;
    private List<AccountHomePost> post;

    public GetAccountHomeRes(AccountInfo accountInfo, AccountCntInfo accountCntInfo, List<StoryGroup> storyGroup, List<AccountHomePost> post) {
        this.userAccountId = accountInfo.getAccountId();
        this.userProfileImgUrl = accountInfo.getProfileImgUrl();
        this.userDescription = accountInfo.getDescription();
        this.userAccountName = accountInfo.getAccountName();

        this.postCnt = accountCntInfo.getPostCnt();
        this.followerCnt = accountCntInfo.getFollowerCnt();
        this.followerCnt = accountCntInfo.getFollowingCnt();

        this.storyGroup = storyGroup;
        this.post = post;
    }

}
