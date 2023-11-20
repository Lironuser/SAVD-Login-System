package com.example.loginserver.Logic;

import com.example.loginserver.Errors.CompanyError;
import com.example.loginserver.Errors.PasswordError;

import java.util.Random;

public class MailCheck {
    private static int sizeOfCode=6;
    private static String path_gmailServer="http://localhost:8080/sendGmail/send";

    public  static String getCodeToGmail(){
        Random random=new Random();
        String code="";
        String codeNow="";
        for (int i = 0; i <sizeOfCode ; i++) {
            codeNow=String.valueOf(random.nextInt(10));
            code+=codeNow;
        }
        return code;
    }

    public static CompanyError checkEmail(String gmail){
        CompanyError e;
        e=ConnectToEmail.connectToServer(path_gmailServer,"post");
        if(e!= CompanyError.GOOD){
            return e;
        }
        if(!gmail.matches(".*[@]*.")){
            return CompanyError.NOT_VALID_MAIL;
        }
        if(!gmail.matches(".*[.com]*.") || !gmail.matches(".*[.org]*.")){
            return CompanyError.NOT_VALID_MAIL;
        }
        String code=getCodeToGmail();
        e=ConnectToEmail.sendToServer(gmail,code);
        if(e!= CompanyError.GOOD){
            return e;
        }
        e = ConnectToEmail.getFromServer();
        return e;
    }
}
