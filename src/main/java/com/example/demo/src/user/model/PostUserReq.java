package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String userId;
    private String userPwd;
    private String userName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String birthday;
}
