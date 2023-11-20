package com.example.loginserver.repository;

import com.example.loginserver.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
    @Query("SELECT e FROM CompanyEntity e WHERE e.name=:name")
    Optional<CompanyEntity> getCompanyByName(@Param("name") String name);

    @Query("SELECT e FROM CompanyEntity e WHERE e.id=:id")
    Optional<CompanyEntity> getCompanyById(@Param("id") long id);
}
