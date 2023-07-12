package com.example.server.service;

import com.example.server.entity.User;
import com.example.server.model.request.UpdateUserRequest;
import com.example.server.model.request.UserRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAll();

    User findById(UUID id);

    User add(UserRequest userRequest);

    User update(UpdateUserRequest request);

    User delete(UUID id);

}
