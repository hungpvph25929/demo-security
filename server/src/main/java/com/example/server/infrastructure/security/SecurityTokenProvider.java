package com.example.server.infrastructure.security;

import com.example.server.entity.User;
import com.example.server.entity.UserToken;
import com.example.server.infrastructure.constant.Constants;
import com.example.server.infrastructure.constant.Role;
import com.example.server.service.impl.UserTokenServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Component
public class SecurityTokenProvider {

    @Autowired
    private UserTokenServiceImpl userTokenService;

    public String generateTokenUser(User userFind) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, Constants.JWTEXPIRATIONINMS);
        Date expiryDate = calendar.getTime();

        String token = Jwts.builder()
                .setSubject(userFind.getEmail())
                .claim("role", String.valueOf(userFind.getRole()))
                .claim("name", userFind.getName())
                .claim("idUser", userFind.getId())
                .claim("birthdate", userFind.getBirthDate())
                .claim("gender", userFind.getGender())
                .claim("phoneNumber", userFind.getPhoneNumber())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, Constants.SECRET)
                .compact();

        UserToken userTokenFindByIdUser = userTokenService.findUserTokenByUserId(userFind.getId());
        if (userTokenFindByIdUser == null) {
            UserToken userToken = new UserToken();
            userToken.setUserId(userFind.getId());
            userToken.setToken(token);
            userToken.setExpired_time(expiryDate.getTime());
            userTokenService.save(userToken);
        } else {
            userTokenFindByIdUser.setToken(token);
            userTokenFindByIdUser.setExpired_time(expiryDate.getTime());
            userTokenService.save(userTokenFindByIdUser);
        }
        return token;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Constants.SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String email = claims.getSubject();
        String role = claims.get("role", String.class);
        String fullName = claims.get("name", String.class);
        String idUser = claims.get("idUser", String.class);
        String birthDate = claims.get("birthdate", String.class);
        String phoneNumber = claims.get("phoneNumber", String.class);
        Boolean gender = claims.get("gender", Boolean.class);

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        User principal = new User();
        principal.setId(UUID.fromString(idUser));
        principal.setPhoneNumber(phoneNumber);
        principal.setGender(gender);
        principal.setEmail(email);
        principal.setName(fullName);
        principal.setRole(Role.valueOf(authority.getAuthority()));
        return new UsernamePasswordAuthenticationToken(principal, token, Collections.singletonList(authority));
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Constants.SECRET)
                    .build()
                    .parseClaimsJws(token);
            Date expirationDate = claims.getBody().getExpiration();
            if (expirationDate.before(new Date())) {
                return false;
            }
            UserToken userToken = userTokenService.findUserTokenByToken(token);
            if (userToken == null) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
