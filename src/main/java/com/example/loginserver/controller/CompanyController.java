package com.example.loginserver.controller;

import com.example.loginserver.Errors.CompanyError;
import com.example.loginserver.dto.CompanyVo;
import com.example.loginserver.entity.CompanyEntity;
import com.example.loginserver.server.CompanyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/company")
@RestController
public class CompanyController {
    @Autowired
    private CompanyServer server;
    private CompanyError e;
    @PostMapping("/create")
    public CompanyError addCompany(@RequestBody CompanyVo companyVO){
        e = server.save(companyVO);
        return e;
    }

    @PutMapping("/update")
    public CompanyError updateCompany(@RequestBody CompanyVo userVO){
        e = server.update(userVO);
        return e;
    }
    @GetMapping("/companyinfo")
    public CompanyVo getCompanyByCompanyId(@RequestBody CompanyVo companyVo){
        CompanyVo company = server.getCompanyByCompanyId(companyVo);
        return company;
    }

}
