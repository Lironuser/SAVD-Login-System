package com.example.loginserver.server;

import com.example.loginserver.Errors.PasswordError;
import com.example.loginserver.Logic.PasswordCheck;
import com.example.loginserver.entity.PasswordEntity;
import com.example.loginserver.repository.PasswordRepository;
import com.example.loginserver.dto.PasswordVo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        e= PasswordCheck.checkPassValid(passwordVo);
        if(e!=PasswordError.GOOD){
            return e;
        }
        PasswordEntity bean= new PasswordEntity();
        BeanUtils.copyProperties(passwordVo,bean);
            passwordRepository.save(bean);
        return PasswordError.GOOD;
    }

    public PasswordError Update(PasswordVo passwordVo){
        PasswordError e;
        e=changePassForUpdate(passwordVo.getCompany_id(),passwordVo.getPassword());
        if(e!=PasswordError.GOOD){
            return e;
        }
        PasswordEntity bean;
        bean=getById(passwordVo.getId());
        BeanUtils.copyProperties(passwordVo,bean);
        passwordRepository.save(bean);
        return PasswordError.GOOD;
    }
    private PasswordEntity getById(long id){
        PasswordEntity user=passwordRepository.findById(id).orElseThrow(()->new NoSuchElementException("Not Found!!!"));
        return user;
    }

    private PasswordError changePassForUpdate(long id,String password){
        Optional<List<PasswordEntity>> passwordEntity;
        passwordEntity=passwordRepository.getAllById(id);
        if(!passwordEntity.isPresent()){
            return PasswordError.COMPANY_NOT_FOUND;
        }
        String passEntity=passwordEntity.get().get(passwordEntity.get().size()-1).getPassword();
        if(passEntity.equals(password)){
            return PasswordError.TheSamePassword;
        }
        for (int i = 0; i < passwordEntity.get().size(); i++) {
            if(passwordEntity.get().get(i).equals(password)){
                return PasswordError.ALREADY_USED;
            }
        }
        return PasswordError.GOOD;
    }

    public String hashedPassword(PasswordVo passwordVo){
        String hashed = BCrypt.hashpw(passwordVo.getPassword(), BCrypt.gensalt());
        return hashed;
    }

    public PasswordError hashedPasswordMatche(String inputPassword, PasswordVo passwordVo){
        if (BCrypt.checkpw(inputPassword, hashedPassword(passwordVo))){
            return PasswordError.GOOD;
        }
        else
            return PasswordError.PASSWORDS_NOT_MATCHES;
    }

}
