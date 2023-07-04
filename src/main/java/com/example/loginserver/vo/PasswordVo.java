package com.example.loginserver.vo;

import lombok.Data;

import java.util.Date;
@Data
public class PasswordVo {
    private Long id;
    private long userId;
    private String password;
    private Date date;
}
