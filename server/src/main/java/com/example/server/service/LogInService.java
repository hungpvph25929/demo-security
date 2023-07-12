package com.example.server.service;

import com.example.server.model.reponse.JwtResponse;
import com.example.server.model.request.LoginRequest;
import jakarta.validation.Valid;

public interface LogInService {

    JwtResponse loginGoogle(String tokenId);

    JwtResponse loginBasic(@Valid LoginRequest request);

    String logout(String token);

}
