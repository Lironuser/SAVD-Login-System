package com.example.loginserver.dto;

import lombok.Data;

@Data
public class LoginVo {
    private String name;
    private String mail;
    private String secret_key;
    private String password;
}
