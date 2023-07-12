package com.example.server.service.impl;

import com.example.server.entity.User;
import com.example.server.infrastructure.constant.Message;
import com.example.server.infrastructure.exception.rest.InvalidTokenException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

public class GoogleTokenVerifier {

    private static final String CLIENT_ID_GOOGLE = "585507045656-viphocp3g9kfi45j63erq7c14295uuch.apps.googleusercontent.com";

    public static User verifyToken(String tokenId) {
        try {
            JsonFactory jsonFactory = new JacksonFactory();
            NetHttpTransport transport = new NetHttpTransport();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(CLIENT_ID_GOOGLE))
                    .build();

            GoogleIdToken idToken = verifier.verify(tokenId);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                User user = new User();
                user.setName(name);
                user.setEmail(email);
                return user;
            } else {
                throw new InvalidTokenException(Message.TOKEN_VERIFICATION_FAILED);
            }
        } catch (Exception e) {
            throw new InvalidTokenException(Message.TOKEN_VERIFICATION_FAILED);
        }
    }

}
