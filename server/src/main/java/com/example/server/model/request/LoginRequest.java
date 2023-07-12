package com.example.server.model.request;

import com.example.server.infrastructure.constant.EntityProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @Email
    @Length(max = EntityProperties.LENGTH_EMAIL)
    private String email;

    @NotBlank
    @Length(max = EntityProperties.LENGTH_PASSWORD)
    private String password;

}
