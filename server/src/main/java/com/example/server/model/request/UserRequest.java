package com.example.server.model.request;

import com.example.server.infrastructure.constant.Constants;
import com.example.server.infrastructure.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Name is empty")
    private String name;

    @NotBlank(message = "Birth date is empty")
    private String birthDate;

    private Boolean gender;

    @NotBlank(message = "Email is empty")
    @Email(message = "Wrong email format")
    @Length(max = 50)
    private String email;

    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER, message = "Wrong phone number format")
    private String phoneNumber;

    @NotBlank
    @Length(max = 30)
    private String password;

    private Role role;

}
