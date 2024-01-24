package com.example.loginserver.server;

import com.example.loginserver.Errors.LoginError;
import com.example.loginserver.Errors.LogsError;
import com.example.loginserver.Errors.PasswordError;
import com.example.loginserver.Logic.PasswordCheck;
import com.example.loginserver.dto.LoginVo;
import com.example.loginserver.dto.LogsVo;
import com.example.loginserver.entity.CompanyEntity;
import com.example.loginserver.entity.PasswordEntity;
import com.example.loginserver.repository.CompanyRepository;
import com.example.loginserver.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServer {
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private LogServer logs;

    public LoginError checkValid(LoginVo loginVo, String connection_ip){
        if (PasswordCheck.checkPassValid(loginVo.getPassword()) == PasswordError.GOOD){
            try {
                Optional<CompanyEntity> companyEntity = companyRepository.getobjIdByMail(loginVo.getMail());
                // מחזיר לי אובייקט של חברה בעזרת המייל
                if (!companyEntity.isPresent()){
                    return LoginError.NOT_FOUND;
                }
                Optional<PasswordEntity> passwordEntity = passwordRepository.getObjByPassword(loginVo.getPassword());
                if (!passwordEntity.isPresent()){
                    return LoginError.NOT_FOUND;
                }
                if (companyEntity.get().getId() < 1) {
                    return LoginError.NOT_FOUND;
                }
                        // בודק אם הם חולקים את אותו המזהה של החברה
                String pass = passwordEntity.get().getPassword();
                long x=companyEntity.get().getId();
                passwordEntity = passwordRepository.getLastObjByCompanyID(x);
                LogsVo logsVo = new LogsVo();
                logsVo.setCompany_id(companyEntity.get().getId());
                logsVo.setPassword(logsVo.getPassword());
                logsVo.setIp(connection_ip);
                // בודק אם המשתמש חסום ומכניס את הלוגים למסד המידע

                if (pass.equals(passwordEntity.get().getPassword())){
                    logsVo.setLog("GOOD");
                    logs.save(logsVo,connection_ip);
                    return LoginError.GOOD;
                }else {
                    logsVo.setLog("Fail");
                    logs.save(logsVo,connection_ip);
                        return LoginError.YOUR_USER_IS_BLOCKED;
                    }
            }catch (Exception e){
                System.out.println(e);
                return LoginError.MISSING_INPUTS;
            }
        }
        return LoginError.ELSE_ERROR;
    }

}
