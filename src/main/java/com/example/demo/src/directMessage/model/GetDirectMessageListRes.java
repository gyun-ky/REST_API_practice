package com.example.demo.src.directMessage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDirectMessageListRes {

    private int roomIdx;
    private String accountId;
    private String profileImgUrl;
    private String lastSentMessage;
}
