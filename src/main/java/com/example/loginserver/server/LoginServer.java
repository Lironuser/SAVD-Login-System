package com.example.loginserver.server;

import com.example.loginserver.Errors.LoginError;
import com.example.loginserver.Errors.PasswordError;
import com.example.loginserver.Logic.PasswordCheck;
import com.example.loginserver.dto.CompanyVo;
import com.example.loginserver.dto.PasswordVo;
import com.example.loginserver.entity.CompanyEntity;
import com.example.loginserver.entity.PasswordEntity;
import com.example.loginserver.repository.CompanyRepository;
import com.example.loginserver.repository.LoginRepository;
import com.example.loginserver.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServer {
    @Autowired
    private LoginRepository loginRepository;
    private PasswordRepository passwordRepository;
    private LoginError e;

    public LoginError checkValid(CompanyVo companyVo, PasswordVo passwordVo){
        if (PasswordCheck.checkPassValid(passwordVo) == PasswordError.GOOD){
            Optional<PasswordEntity> passwordEntity;
            long company_id = 0;
            try {
                company_id = loginRepository.getCompanyIdByMail(companyVo.getMail());
                if (company_id >= 1) {
                    passwordEntity = passwordRepository.getPasswordById(company_id);
                    if (passwordEntity.isPresent()){
                        return LoginError.GOOD;
                    }
                }
            }catch (Exception e){
                System.out.println(e);
                return LoginError.MISSING_INPUTS;
            }
        }
        return LoginError.ELSE_ERROR;
    }

}
