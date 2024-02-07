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

import static com.example.loginserver.Errors.PasswordError.GOOD;
import static com.example.loginserver.Logic.PasswordCheck.hashPassword;

@Service
public class PasswordServer {
    @Autowired
    private PasswordRepository passwordRepository;

    public PasswordServer(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public PasswordError save(PasswordVo passwordVo){
        PasswordError e;
        e = PasswordCheck.checkPassValid(passwordVo.getPassword());
        if(e!= GOOD){
            return e;
        }
        passwordVo.setPassword(hashPassword(passwordVo.getPassword()));
        PasswordEntity bean= new PasswordEntity();
        BeanUtils.copyProperties(passwordVo,bean);
            passwordRepository.save(bean);
        return GOOD;
    }

    public PasswordError Update(PasswordVo passwordVo){
        PasswordError e;
        e=changePassForUpdate(passwordVo);
        if(e!= GOOD){
            return e;
        }
        PasswordEntity bean = getById(passwordVo.getId());
        passwordVo.setPassword(hashPassword(passwordVo.getPassword()));
        BeanUtils.copyProperties(passwordVo,bean);
        passwordRepository.save(bean);
        return GOOD;
    }
    private PasswordEntity getById(long id){
        PasswordEntity user=passwordRepository.findById(id).orElseThrow(()->new NoSuchElementException("Not Found!!!"));
        return user;
    }

    private PasswordError changePassForUpdate(PasswordVo passwordVo){
        Optional<List<PasswordEntity>> passwordEntityList;
        passwordEntityList=passwordRepository.getAllObjById(passwordVo.getCompany_id());
        if(!passwordEntityList.isPresent()){
            return PasswordError.COMPANY_NOT_FOUND;
        }
        Optional<PasswordEntity> passwordEntity;
        passwordEntity = passwordRepository.getObjById(passwordVo.getCompany_id());
        if(hashPassword(passwordVo.getPassword()).equals(passwordEntity.get())){
            return PasswordError.TheSamePassword;
        }
        for (int i = 0; i < passwordEntityList.get().size(); i++) {
            if(passwordEntityList.get().get(i).equals(passwordVo.getPassword())){
                return PasswordError.ALREADY_USED;
            }
        }
        return GOOD;
    }

    public String hashedPassword(PasswordVo passwordVo){
        String hashed = BCrypt.hashpw(passwordVo.getPassword(), BCrypt.gensalt());
        return hashed;
    }

}
