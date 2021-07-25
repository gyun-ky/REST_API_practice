package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PostPostReq {

    private String contents;
    private String locationTag;
    private List<PostImage> images;
    private List<HashTag> hashTags;
}
