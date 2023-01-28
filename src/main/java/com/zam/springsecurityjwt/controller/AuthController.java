package com.zam.springsecurityjwt.controller;


import com.zam.springsecurityjwt.dto.LoginRequest;
import com.zam.springsecurityjwt.dto.RegisterRequest;
import com.zam.springsecurityjwt.dto.AuthResponse;
import com.zam.springsecurityjwt.service.impl.UserServiceImpl;
import com.zam.springsecurityjwt.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody  RegisterRequest registerRequest){
        return userServiceImpl.register(registerRequest)
                .map(authResponse -> Helper.generateResponse("Success registration", HttpStatus.CREATED, authResponse))
                .orElse(Helper.generateResponse("Failed to registration , username has be already used", HttpStatus.BAD_REQUEST, null));
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        try {
            AuthResponse authResponse = userServiceImpl.login(loginRequest);
            return ResponseEntity.ok(authResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
