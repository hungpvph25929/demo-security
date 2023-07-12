package com.example.server.service;

import com.example.server.entity.UserToken;
import com.example.server.model.reponse.JwtResponse;

import java.util.UUID;

public interface UserTokenService {

    UserToken findUserTokenByToken(String token);

    UserToken findUserTokenByUserId(UUID userId);

    void save(UserToken userToken);

    JwtResponse findUserByToken(String token);

}
