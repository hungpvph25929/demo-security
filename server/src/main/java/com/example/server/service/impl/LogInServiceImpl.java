package com.example.server.service.impl;

import com.example.server.entity.User;
import com.example.server.infrastructure.constant.Message;
import com.example.server.infrastructure.exception.rest.RestApiException;
import com.example.server.infrastructure.security.SecurityTokenProvider;
import com.example.server.model.reponse.JwtResponse;
import com.example.server.model.request.LoginRequest;
import com.example.server.repository.UserRepository;
import com.example.server.repository.UserTokenRepository;
import com.example.server.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LogInServiceImpl implements LogInService {

    @Autowired
    private SecurityTokenProvider securityTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse loginGoogle(String tokenId) {
        User userVerify = GoogleTokenVerifier.verifyToken(tokenId);
        User userFind = userRepository.findUserByEmail(userVerify.getEmail());
        if (userFind == null) {
            throw new RestApiException(Message.USER_NOT_EXIST);
        }
        String jwtToken = securityTokenProvider.generateTokenUser(userFind);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwtToken);
        return jwtResponse;
    }

    @Override
    public JwtResponse loginBasic(@RequestBody LoginRequest request) {
        User userFind = userRepository.findUserByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), userFind.getPassword())) {
            throw new RestApiException(Message.EMAIL_OR_PASSWORD_INCORRECT);
        }
        String jwtToken = securityTokenProvider.generateTokenUser(userFind);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwtToken);
        return jwtResponse;
    }

    @Override
    public String logout(String token) {
        userTokenRepository.deleteUserTokenByToken(token);
        return "Successful logout !";
    }

}
