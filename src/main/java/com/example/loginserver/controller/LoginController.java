package com.example.loginserver.controller;

import com.example.loginserver.Errors.CompanyError;
import com.example.loginserver.Errors.LoginError;
import com.example.loginserver.dto.CompanyVo;
import com.example.loginserver.dto.PasswordVo;
import com.example.loginserver.server.CompanyServer;
import com.example.loginserver.server.LoginServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/login")
@RestController
public class LoginController {
    @Autowired
    private LoginServer server;
    private LoginError e;

    @PutMapping("/Connection")
    public LoginError checkConnection(CompanyVo companyVo, PasswordVo passwordVo){
        e = server.checkValid(companyVo, passwordVo);
        return e;
    }
}
