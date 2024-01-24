package com.example.loginserver.controller;

import com.example.loginserver.Errors.CompanyError;
import com.example.loginserver.Errors.LoginError;
import com.example.loginserver.dto.CompanyVo;
import com.example.loginserver.dto.LoginVo;
import com.example.loginserver.dto.PasswordVo;
import com.example.loginserver.server.CompanyServer;
import com.example.loginserver.server.LoginServer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/login")
@RestController
public class LoginController {
    @Autowired
    private LoginServer server;
    private LoginError e;

    @PostMapping("/Connection")
    public LoginError checkConnection(@RequestBody LoginVo login, HttpServletRequest request){
        e = server.checkValid(login, request.getRemoteAddr());
        return e;
    }
}
