package com.group.nice.restaurantapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group.nice.restaurantapi.dtos.AuthUserDTO;
import com.group.nice.restaurantapi.dtos.UserBaseDTO;
import com.group.nice.restaurantapi.dtos.UserDTO;
import com.group.nice.restaurantapi.services.user.IUserService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private IUserService userService;
    
    @PostMapping("/signin")
    public UserBaseDTO signin(@RequestBody AuthUserDTO user) {
        return userService.signin(user);
    }
    @PostMapping("/signup")
    public UserBaseDTO signup(@RequestBody UserDTO user) {
        return userService.signup(user);
    }
}
