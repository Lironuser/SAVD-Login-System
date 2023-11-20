package com.example.loginserver.dto;

import com.example.loginserver.Errors.PasswordError;
import lombok.Data;

@Data
public class PasswordVo {
    private Long id;
    private long company_id;
    private String password;

    //Addon
    private PasswordError error;
}
