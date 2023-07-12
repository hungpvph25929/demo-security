package com.example.server.infrastructure.exception.rest;

import com.example.server.infrastructure.constant.Message;

public class RestApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public RestApiException(Message statusCode) {
        this.message = statusCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
