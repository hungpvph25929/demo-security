package com.example.server.controller;

import com.example.server.infrastructure.common.ResponseObject;
import com.example.server.model.request.UpdateUserRequest;
import com.example.server.model.request.UserRequest;
import com.example.server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/mentor/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseObject getAll() {
        return new ResponseObject(userService.getAll());
    }

    @GetMapping("{id}")
    public ResponseObject findById(@PathVariable("id") String id) {
        return new ResponseObject(userService.findById(UUID.fromString(id)));
    }

    @PostMapping("/add")
    public ResponseObject add(@Valid @RequestBody UserRequest request) {
        return new ResponseObject(userService.add(request));
    }

    @PutMapping("/update")
    public ResponseObject update(@Valid @RequestBody UpdateUserRequest request) {
        return new ResponseObject(userService.update(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(userService.delete(UUID.fromString(id)));
    }

}
