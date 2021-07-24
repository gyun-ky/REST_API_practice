package com.example.demo.src.feed.model;

import com.example.demo.src.account.model.PostAccountRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetFeedRes {

    private String userProfileImgUrl;
    private String userAccountId;

    private List<Story> story;

    private List<Post> post;

//    private List<PostImage> postImage;

    public GetFeedRes(UserInfo userInfo, List<Story> storyList, List<Post> postList){

        this.userAccountId = userInfo.getUserAccountId();
        System.out.println("[GETFEEDRES] 생성자1");
        this.userProfileImgUrl = userInfo.getUserProfileImgUrl();
        System.out.println("[GETFEEDRES] 생성자2");
        this.story = storyList;
        System.out.println("[GETFEEDRES] 생성자3");
        this.post = postList;
        System.out.println("[GETFEEDRES] 생성자4");
//        this.postImage = postImage;

    }


}
