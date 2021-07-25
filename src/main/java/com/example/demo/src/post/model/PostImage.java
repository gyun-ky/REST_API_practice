package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImage {

    private String imageUrl;

    public PostImage(){

    }

    public PostImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
