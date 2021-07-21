package com.example.demo.src.accountHome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AccountHomePost {

    private int postIdx;
    private String imageUrl;
    private String contents;
}
