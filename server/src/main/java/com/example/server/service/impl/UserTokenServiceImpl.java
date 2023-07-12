package com.example.server.service.impl;

import com.example.server.entity.UserToken;
import com.example.server.infrastructure.constant.Message;
import com.example.server.infrastructure.exception.rest.RestApiException;
import com.example.server.model.reponse.JwtResponse;
import com.example.server.model.request.UserRequest;
import com.example.server.repository.UserTokenRepository;
import com.example.server.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Override
    public UserToken findUserTokenByToken(String token) {
        return userTokenRepository.findUserToken(token);
    }

    @Override
    public UserToken findUserTokenByUserId(UUID userId) {
        return userTokenRepository.findUserTokenByIdUser(userId);
    }

    @Override
    public void save(UserToken userToken) {
        userTokenRepository.save(userToken);
    }

    @Override
    public JwtResponse findUserByToken(String token) {
        UserRequest userRequest = userTokenRepository.findUserByToken(token);
        if (userRequest == null) {
            throw new RestApiException(Message.USER_NOT_EXIST);
        }
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        return jwtResponse;
    }

}
