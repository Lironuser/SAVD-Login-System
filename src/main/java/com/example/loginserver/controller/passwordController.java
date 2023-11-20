package com.example.loginserver.controller;

import com.example.loginserver.Errors.PasswordError;
import com.example.loginserver.dto.PasswordVo;
import com.example.loginserver.server.PasswordServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/pass")
@RestController
public class passwordController {
    @Autowired
    private PasswordServer server;
    private PasswordError e;

    @PostMapping("/createPassword")
    public PasswordError addPassword(@RequestBody PasswordVo passVo){
        e = server.save(passVo);
        return e;
    }
}
