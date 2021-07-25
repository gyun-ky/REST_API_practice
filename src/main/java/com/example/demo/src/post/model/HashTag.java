package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HashTag {

    private String tagName;

    public HashTag(){

    }

    public HashTag(String tagName) {
        this.tagName = tagName;
    }
}
