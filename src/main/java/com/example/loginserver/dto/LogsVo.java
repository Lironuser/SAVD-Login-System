package com.example.loginserver.dto;

import com.example.loginserver.Errors.LogsError;
import lombok.Data;

import java.util.Date;

@Data
public class LogsVo {
    private Long id;
    private long company_id;
    private String password;
    private String log;
    private String ip;
    //Addon
    private LogsError error;
}
