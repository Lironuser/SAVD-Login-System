package com.example.loginserver.repository;

import com.example.loginserver.entity.LogEntity;
import com.example.loginserver.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<LogEntity,Long> {
    @Query(value = "SELECT * FROM LogEntity where company_id=?1 ORDER BY \"date\" DESC LIMIT ?2",nativeQuery = true)
    Optional<List<LogEntity>> CheckBlock(long id, int sizeBLock);
    @Query(value = "SELECT * FROM LogEntity WHERE ip = ?1 AND \"date\" >= ?2", nativeQuery = true)
    Optional<List<LogEntity>> CheckSpam(String ip, Timestamp timestamp);
    @Query(value = "SELECT e FROM CompanyEntity e WHERE e.id=:company_id")
    Optional<LogEntity>getCompanyByCompanyId(@Param("company_id")long id);
    @Query(value = "SELECT e FROM PasswordEntity e where e.company_id=:id ")
    List<PasswordEntity> getPasswordByCompanyId(@Param("id") long userId);

    //@Query(value = "SELECT * FROM logs WHERE user_id=?1 AND seccess=true ORDER BY id DESC LIMIT 1",nativeQuery = true)
    //Optional<LogEntity> getLastSeccessLogin(long user_id);

}