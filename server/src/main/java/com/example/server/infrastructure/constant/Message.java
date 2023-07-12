package com.example.server.infrastructure.constant;

import com.example.server.util.PropertiesReader;

public enum Message {

    USER_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.USER_NOT_EXIST)),

    EMAIL_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.EMAIL_NOT_EXIST)),

    TOKEN_VERIFICATION_FAILED(PropertiesReader.getProperty(PropertyKeys.TOKEN_VERIFICATION_FAILED)),

    EMAIL_OR_PASSWORD_INCORRECT(PropertiesReader.getProperty(PropertyKeys.EMAIL_OR_PASSWORD_INCORRECT)),

    INVALID_TOKEN(PropertiesReader.getProperty(PropertyKeys.INVALID_TOKEN));

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
