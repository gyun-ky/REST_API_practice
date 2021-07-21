package com.example.demo.src.accountHome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountInfo {

    private String accountId;
    private String accountName;
    private String profileImgUrl;
    private String description;
}
