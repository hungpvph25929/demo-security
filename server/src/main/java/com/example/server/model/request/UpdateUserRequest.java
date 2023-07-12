package com.example.server.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateUserRequest extends UserRequest{

    private UUID id;
}
