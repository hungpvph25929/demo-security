package com.example.server.controller;

import com.example.server.infrastructure.common.ResponseObject;
import com.example.server.model.request.LoginRequest;
import com.example.server.model.request.UserRequest;
import com.example.server.service.LogInService;
import com.example.server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin("*")
public class AuthRestController {

    @Autowired
    private LogInService logInService;

    @Autowired
    private UserService userService;

    @PostMapping("/login-google/{tokenId}")
    public ResponseObject loginGoogle(@PathVariable("tokenId") String tokenId) {
        return new ResponseObject(logInService.loginGoogle(tokenId));
    }

    @PostMapping("/login-basic")
    public ResponseObject loginBasic(@RequestBody LoginRequest request) {
        return new ResponseObject(logInService.loginBasic(request));
    }

    @PostMapping("/register")
    public ResponseObject register(@RequestBody UserRequest request) {
        return new ResponseObject(userService.add(request));
    }

    @PostMapping("/logout")
    public ResponseObject logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        new SecurityContextLogoutHandler().logout(request, null, null);
        return new ResponseObject(logInService.logout(token));
    }

}
