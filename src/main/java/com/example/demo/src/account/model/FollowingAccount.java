package com.example.demo.src.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FollowingAccount {

    private int followedIdx;
    private String accountId;
    private String accountName;
    private String profileImgUrl;
}
