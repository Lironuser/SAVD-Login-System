package com.example.loginserver.repository;

import com.example.loginserver.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordEntity,Long> {
    @Query(value = "SELECT * FROM password WHERE company_id=?1",nativeQuery = true)
    Optional<List<PasswordEntity>>getAllById(long company_id);

    @Query("SELECT e FROM PasswordEntity e WHERE e.id=:id")
    Optional<PasswordEntity> getPasswordById(@Param("id") long id);
}