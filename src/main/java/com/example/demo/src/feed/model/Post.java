package com.example.demo.src.feed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Post {

    private int idx;
    private String profileImgUrl;
    private String accountId;
    private String locationTag;
    private boolean scrap;
    private int likeCnt;
    private String createdAt;
    private String contents;
    private List<PostImage> postImage;

    public Post(int idx, String profileImgUrl, String accountId, String locationTag, boolean scrap, int likeCnt, String createdAt, String contents) {
        this.idx = idx;
        this.profileImgUrl = profileImgUrl;
        this.accountId = accountId;
        this.locationTag = locationTag;
        this.scrap = scrap;
        this.likeCnt = likeCnt;
        this.createdAt = createdAt;
        this.contents = contents;
    }

    public void setPostImage(PostImage newPostImage){
        if(this.postImage == null){
            this.postImage = new ArrayList<PostImage>();
        }
        this.postImage.add(newPostImage);

    }
}
