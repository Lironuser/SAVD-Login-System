package com.example.loginserver.controller;

import com.example.loginserver.Errors.LogsError;
import com.example.loginserver.dto.LogsVo;
import jakarta.servlet.http.HttpServletRequest;
import com.example.loginserver.server.LogServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/logs")
@RestController
public class LogController {
    @Autowired
    private LogServer server;
    private LogsError e;

    @PostMapping("/save")
    public LogsError save_log(@RequestBody LogsVo log, HttpServletRequest request){
        e = server.save(log, request.getRemoteAddr());
        return e;
    }
}
