package com.example.loginserver.server;

import Errors.PasswordError;
import Logic.PasswordCheck;
import com.example.loginserver.entity.PasswordEntity;
import com.example.loginserver.repository.PasswordRepository;
import com.example.loginserver.vo.PasswordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PasswordServer {
    @Autowired
    private PasswordRepository passwordRepository;

    public PasswordServer(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public PasswordError save(PasswordVo passwordVo){
        PasswordError e;
        e=changePassForUpdate(passwordVo.getUserId(), passwordVo.getPassword());
        if(e!=PasswordError.GOOD){
            return e;
        }
        e= PasswordCheck.checkPassObject(PasswordVo);
        if(e!=PasswordError.GOOD){
            return e;
        }
        PasswordEntity bean= new PasswordEntity();
        bean.setDate(new Date());
        BeanUtils.copyProperties(passwordVo,bean);
        passwordRepository.save(bean);
        return PasswordError.GOOD;
    }

    public PasswordError delete(long id){
        passwordRepository.deleteById(id);
        return PasswordError.GOOD;
    }
    public PasswordError update(PasswordVo passwordVo){
        PasswordError e;
        e=changePassForUpdate(passwordVo.getUserId(),passwordVo.getPassword());
        if(e!=PasswordError.GOOD){
            return e;
        }
        PasswordEntity bean;
        bean=geyById(passwordVo.getId());
        BeanUtils.copyProperties(passwordVo,bean);
        passwordRepository.save(bean);
        return PasswordError.GOOD;
    }
    private PasswordEntity geyById(long id){
        PasswordEntity user=passwordRepository.findById(id).orElseThrow(()->new NoSuchElementException("Not Found!!!"));
        return user;
    }
    private PasswordError changePassForUpdate(long id,String password){
        Optional<List<PasswordEntity>> passwordEntity;
        passwordEntity=passwordRepository.getAllById(id);
        if(!passwordEntity.isPresent()){
            return PasswordError.UserNotFound;
        }
        String passEntity=passwordEntity.get().get(passwordEntity.get().size()-1).getPassword();
        if(passEntity.equals(password)){
            return PasswordError.TheSamePassword;
        }
        for (int i = 0; i < passwordEntity.get().size(); i++) {
            if(passwordEntity.get().get(i).equals(password)){
                return PasswordError.PastUse;
            }
        }
        return PasswordError.GOOD;
    }

}
