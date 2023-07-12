package com.example.server.service.impl;

import com.example.server.entity.User;
import com.example.server.infrastructure.constant.Message;
import com.example.server.infrastructure.exception.rest.RestApiException;
import com.example.server.model.request.UpdateUserRequest;
import com.example.server.model.request.UserRequest;
import com.example.server.repository.UserRepository;
import com.example.server.service.UserService;
import com.example.server.util.ConvertStringToDate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new RestApiException(Message.USER_NOT_EXIST);
        }
        return user.get();
    }

    @Override
    public User add(@Valid UserRequest userRequest) {
        User user = new User();
        User findUserByEmail = userRepository.findUserByEmail(userRequest.getEmail());
        if (findUserByEmail != null) {
            throw new RestApiException(Message.EMAIL_NOT_EXIST);
        }
        user.setName(userRequest.getName());
        Date date = ConvertStringToDate.convert(userRequest.getBirthDate());
        user.setBirthDate(date);
        user.setGender(userRequest.getGender());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());
        userRepository.save(user);
        return userRepository.save(user);
    }

    @Override
    public User update(UpdateUserRequest request) {
        User user = findById(request.getId());
        if (user == null) {
            throw new RestApiException(Message.USER_NOT_EXIST);
        }
        user.setName(request.getName());
        Date date = ConvertStringToDate.convert(request.getBirthDate());
        user.setBirthDate(date);
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    @Override
    public User delete(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new RestApiException(Message.USER_NOT_EXIST);
        }
        userRepository.delete(user.get());
        return user.get();
    }

}
