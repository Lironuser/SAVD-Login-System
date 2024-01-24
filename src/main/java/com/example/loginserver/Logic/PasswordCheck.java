package com.example.loginserver.Logic;

import com.example.loginserver.Errors.PasswordError;
import com.example.loginserver.dto.PasswordVo;

public class PasswordCheck {
    private static PasswordError checkLength(String password){
        if(password.length()<11 || password.length()>16){
            return PasswordError.LENGTH_ERROR;
        }
        return PasswordError.GOOD;
    }
    private static PasswordError checkChars(String password){
        if(!password.matches(".*[A-Z]*.")){
            return PasswordError.NO_BIG_CHAR;
        }
        if(!password.matches(".*[a-z]*.")){
            return PasswordError.NO_SMALL_CHAR;
        }
        if(!password.matches(".*[0-9]*.")){
            return PasswordError.NO_NUMBER;
        }
        if (!password.matches(".*[!]*.") || !password.matches(".*[_]*.")){
            return PasswordError.NO_SPECIAL_CHAR;
        }
        return PasswordError.GOOD;
    }
    public static PasswordError checkPassValid(String password){
        PasswordError e;
        e=checkLength(password);
        if(e!= PasswordError.GOOD){
            return e;
        }
        e=checkChars(password);
        return e;
    }
}
