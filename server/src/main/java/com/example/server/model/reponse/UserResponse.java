package com.example.server.model.reponse;

import java.sql.Date;
import java.util.UUID;

public interface UserResponse {

    UUID getId();

    String getFullname();

    String getEmail();

    Date getBirthDate();

    String getPhonenumber();

    Boolean getGender();


    Integer getRole();

}
