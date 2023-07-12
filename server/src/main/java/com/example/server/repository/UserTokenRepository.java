package com.example.server.repository;

import com.example.server.entity.UserToken;
import com.example.server.model.request.UserRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {
    @Query(value = """
            SELECT id, token, expired_time, userid
            FROM "user_token"
            WHERE token = :token
            """, nativeQuery = true)
    UserToken findUserToken(@Param("token") String token);

    @Query(value = """
            SELECT id, token, expired_time, userid
            FROM "user_token" 
            WHERE userid = :userId
            """, nativeQuery = true)
    UserToken findUserTokenByIdUser(@Param("userId") UUID userId);

    @Query(value = """
            SELECT a.id, a.name, a.email, a.birthdate, a.phone_number, a.gender, a.role
            FROM "users" a JOIN "usertoken" b ON a.id = b.userid
            WHERE b.token = :token
            """, nativeQuery = true)
    UserRequest findUserByToken(@Param("token") String token);

    @Modifying
    @Transactional
    void deleteUserTokenByToken(String token);
}
