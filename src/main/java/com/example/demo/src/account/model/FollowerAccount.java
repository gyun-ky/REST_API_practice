package com.example.demo.src.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FollowerAccount {
    private int followerIdx;
    private String accountId;
    private String accountName;
    private String profileImgUrl;
}
