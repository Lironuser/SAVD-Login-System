package com.example.loginserver.repository;

import com.example.loginserver.entity.CompanyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository {
    @Query("SELECT id FROM CompanyEntity WHERE mail=:mail")
    long getCompanyIdByMail(@Param("mail") String mail);
}
