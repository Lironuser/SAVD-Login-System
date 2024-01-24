package com.example.loginserver.Logic;

import com.example.loginserver.Errors.CompanyError;

public class CompanyCheck {
    public static CompanyError checkCompanyName(String comp_name){
        if(!comp_name.matches(".*[A-Z]*.")){
            return CompanyError.NOT_VALID_NAME;
        }
        return CompanyError.GOOD;
    }
}
