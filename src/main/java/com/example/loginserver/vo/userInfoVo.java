package com.example.loginserver.vo;

import lombok.Data;

@Data
public class userInfoVo {
    private int id;
    private String nickname;
    private String firstName;
    private String lastName;
    private String mail;
    private String secretKey;
}
