package com.example.loginserver.server;

import Errors.UserInfoError;
import com.example.loginserver.entity.UserInfoEntity;
import com.example.loginserver.repository.UserInfoRepository;
import com.example.loginserver.vo.userInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServer {
    @Autowired
    private UserInfoRepository repository;
    public UserInfoError save(userInfoVo user){
        String userName;
        userName=user.getNickname();
        Optional<UserInfoEntity> userEntity;
        userEntity=repository.getUserByUserName(userName);
        if(userEntity.isPresent()){
            return UserInfoError.UserExist;
        }
        try {
            UserInfoEntity bean=new UserInfoEntity();
            BeanUtils.copyProperties(user,bean);
            repository.save(bean);
        }catch (Exception e){
            System.out.println(e);
            return UserInfoError.ElseError;
        }
        return UserInfoError.GOOD;
    }

    public UserInfoError delete(userInfoVo user){
        String userName;
        userName=user.getNickname();
        Optional<UserInfoEntity> userEntity;
        userEntity=repository.getUserByUserName(userName);
        if(userEntity.isPresent()){
            return UserInfoError.UserExist;
        }
        try {
            int user_id;
            user_id = user.getId();
            repository.deleteById(user_id);
        }catch (Exception e){
            System.out.println(e);
            return UserInfoError.ElseError;
        }
        return UserInfoError.GOOD;
    }

    public UserInfoError update(userInfoVo user){
        String userName;
        userName=user.getNickname();
        Optional<UserInfoEntity> userEntity;
        userEntity=repository.getUserByUserName(userName);
        if(userEntity.isPresent()){
            return UserInfoError.UserExist;
        }

        try{
            UserInfoEntity bean=new UserInfoEntity();
            BeanUtils.copyProperties(user,bean);
            repository.save(bean);
        }catch (Exception e){
            System.out.println(e);
            return UserInfoError.ElseError;
        }
        return UserInfoError.GOOD;
    }
}
