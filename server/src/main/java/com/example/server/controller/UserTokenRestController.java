package com.example.server.controller;

import com.example.server.infrastructure.common.ResponseObject;
import com.example.server.service.UserTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user-token")
public class UserTokenRestController {

    @Autowired
    private UserTokenService userTokenService;

    @GetMapping("/get-my-user")
    public ResponseObject getMe(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        return new ResponseObject(userTokenService.findUserByToken(token));
    }

}
