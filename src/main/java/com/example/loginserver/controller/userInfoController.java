package com.example.loginserver.controller;

import Errors.UserInfoError;
import Logic.UserInfoCheck;
import com.example.loginserver.entity.UserInfoEntity;
import com.example.loginserver.repository.UserInfoRepository;
import com.example.loginserver.server.UserInfoServer;
import com.example.loginserver.vo.userInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/User")
@RestController
public class userInfoController {
    @Autowired
    private UserInfoServer server;
    private UserInfoError e;
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setPort(8081);
    }
    @PostMapping("/save")
    public UserInfoError save(@RequestBody userInfoVo user){
        UserInfoError e = UserInfoError.GOOD;
        return e;
    }

    public UserInfoServer save(@RequestBody UserInfoCheck userVO){
        UserInfoServer e;
        e = new UserInfoCheck(userVO);
        if(e != UserInfoServer.GOOD){
            return e;
        }
        String secretKey= UserInfoCheck.getSecretKey();
        userInfoVo.setSecretKey(secretKey);
        UserInfoEntity bean= new UserInfoEntity();
        BeanUtils.copyProperties(userVO,bean);
        UserInfoRepository.save(bean);
        return UserInfoServer.GOOD;

    }
    public UserInfoError delete(long id){
        UserInfoEntity user=getById(id);
        if(user==null){
            return UserInfoError.UserNotFound;
        }
        UserInfoRepository.deleteById(id);
        return UserInfoError.GOOD;
    }
}
