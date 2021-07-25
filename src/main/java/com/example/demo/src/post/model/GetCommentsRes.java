package com.example.demo.src.post.model;

import com.example.demo.src.post.PostDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class GetCommentsRes {

    private String userProfileImgUrl;
    private String userAccountId;
    private String postContents;
    private String createdAt;

    private List<Comment> comments;

    public GetCommentsRes(PostInfo postInfo, List<Comment> comments) {
        this.userProfileImgUrl = postInfo.getUserProfileImgUrl();
        this.userAccountId = postInfo.getUserAccountId();
        this.postContents = postInfo.getPostContents();
        this.createdAt = postInfo.getCreatedAt();
        this.comments = comments;
    }
}
