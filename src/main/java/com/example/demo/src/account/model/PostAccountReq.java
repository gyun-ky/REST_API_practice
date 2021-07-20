package com.example.demo.src.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostAccountReq {
    private String userIdx;
    private String accountId;
    private String accountName;
    private String website = "null";
    private String description = "null";
    private String profileImgUrl = "null";
    private String status = "null";
}
