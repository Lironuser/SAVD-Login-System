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
    @Query(value = "SELECT * FROM log where company_id=?1 ORDER BY \"time\" DESC LIMIT ?2",nativeQuery = true)
    Optional<List<LogEntity>> CheckBlock(long id, int sizeBLock);
    @Query(value = "SELECT * FROM log WHERE ip = ?1 AND \"time\"  >= ?2", nativeQuery = true)
    Optional<List<LogEntity>> CheckSpam(String ip, Timestamp timestamp);
}