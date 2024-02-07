package com.example.loginserver.server;

import com.example.loginserver.Authenticator;
import com.example.loginserver.Errors.CompanyError;
import com.example.loginserver.Logic.CompanyCheck;
import com.example.loginserver.dto.CompanyVo;
import com.example.loginserver.entity.CompanyEntity;
import com.example.loginserver.repository.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServer {
    @Autowired
    private CompanyRepository repository;
    public CompanyError save(CompanyVo company){
        Optional<CompanyEntity> userEntity;
        userEntity=repository.getObjByName(company.getComapny_name());
        if(userEntity.isPresent()){ //אם יש כבר חברה כזאת
            return CompanyError.COMPANY_EXIT;
        }
        if (CompanyCheck.checkCompanyName(company.getComapny_name()) != CompanyError.GOOD){
            return CompanyError.NOT_VALID_NAME;
        }
        try {
            CompanyEntity bean=new CompanyEntity();
            company.setSecret_key(Authenticator.generateSecretKey());
            BeanUtils.copyProperties(company,bean); //מעתיק את הנתונים ל"שעוית"
            repository.save(bean);
        }catch (Exception e){
            System.out.println(e);
            return CompanyError.ELSE_ERROR;
        }
        return CompanyError.GOOD;
    }

    public CompanyError update(CompanyVo company){
        Optional<CompanyEntity> companyEntity;
        companyEntity=repository.getObjById(company.getId());
        if (companyEntity.isPresent()){
            if (CompanyCheck.checkCompanyName(company.getComapny_name()) != CompanyError.GOOD){
                return CompanyError.NOT_VALID_NAME;
            }
            try{
                CompanyEntity bean = new CompanyEntity();
                BeanUtils.copyProperties(companyEntity.get(),bean);
                if (company.getComapny_name() != null){ //האם המשתמש רוצה לשנות את השם של החברה
                    bean.setComapny_name(company.getComapny_name());
                }
                if (company.getMail() != null ){    //האם המשתמש רוצה לשנות את המייל של החברה
                    if (checkCompany(bean) == CompanyError.GOOD){
                        bean.setMail(company.getMail());
                    }
                }
                repository.save(bean);
            }catch (Exception e){
                System.out.println(e);
                return CompanyError.ELSE_ERROR;
            }
            return CompanyError.GOOD;
        }
        return CompanyError.COMPANY_NOT_FOUND;
    }

    private CompanyError checkCompany(CompanyEntity company) {
        CompanyError e;
        e = checkComapnyIsSystem(company.getId());  //בודק האם קיימת כבר חברה כזאת
        if (e != CompanyError.GOOD) {
            return e;
        }

        return CompanyError.GOOD;
    }
    private CompanyError checkComapnyIsSystem(long id){
        Optional<CompanyEntity> user = Optional.ofNullable(getCompanyById(id));
        if(!user.isPresent()){  //האם החברה לא קיימת במסד המידע?
            return CompanyError.GOOD;
        }
        return CompanyError.ELSE_ERROR;
    }
    private CompanyEntity getCompanyById(long id){
        CompanyEntity company;
        company=repository.getById(id);
        return company;
    }

    public  CompanyVo getCompanyByCompanyId(CompanyVo companyVo){
        Optional<CompanyEntity> companyEntity;
        companyEntity = repository.getObjById(companyVo.getId());
        if (companyEntity.isPresent()){
            BeanUtils.copyProperties(companyEntity, companyVo);
            return companyVo;
        }
        companyVo.setError(CompanyError.COMPANY_NOT_FOUND);
        return companyVo;
    }
}
