package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.model.dto.UserRegisterDto;
import com.example.bookstoreapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto){
        userService.registerUser(userRegisterDto);
    }

}
