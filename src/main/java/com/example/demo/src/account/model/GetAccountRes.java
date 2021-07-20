package com.example.demo.src.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAccountRes {
    private String profileImageURl;
    private String accountName;
    private String accountId;
    private String website;
    private String description;
}
