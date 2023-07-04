package com.example.loginserver.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LogsVo {
    private Long id;
    private long userId;
    private String password;
    private String ip;
    private boolean success;
    private Date date;
}
