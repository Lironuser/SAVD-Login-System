package com.example.loginserver.repository;

import com.example.loginserver.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity,Integer> {
    @Query("SELECT e FROM UserInfoEntity e WHERE e.nickname=:userName")
    Optional<UserInfoEntity> getUserByUserName(@Param("userName") String userName);
}
