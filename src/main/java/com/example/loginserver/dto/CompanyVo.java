package com.example.loginserver.dto;

import com.example.loginserver.Errors.CompanyError;
import lombok.Data;

@Data
public class CompanyVo {
    private long id;
    private String comapny_name;
    private String mail;
    private String secret_key;
    //Addon
    private CompanyError error;


}
