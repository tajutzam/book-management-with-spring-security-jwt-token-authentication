package com.zam.springsecurityjwt.controller;


import com.zam.springsecurityjwt.dto.*;
import com.zam.springsecurityjwt.service.EmailService;
import com.zam.springsecurityjwt.service.impl.UserServiceImpl;
import com.zam.springsecurityjwt.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private EmailService emailService;

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody  RegisterRequest registerRequest){
        return userServiceImpl.register(registerRequest)
                .map(authResponse -> Helper.generateResponse("Success registration , login to get your token authentication", HttpStatus.CREATED, authResponse))
                .orElse(Helper.generateResponse("Failed to registration , username has be already used", HttpStatus.BAD_REQUEST, null));
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        try {
            LoginResponse authResponse = userServiceImpl.login(loginRequest);
            return ResponseEntity.ok(authResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("sendEmail")
    public ResponseEntity<Object> sendEmail(@RequestBody KeyDTO<String> keyDTO){
        boolean email = userServiceImpl.sendEmail(keyDTO.getKey());
        if(email){
            return Helper.generateResponse("success send email activation" , HttpStatus.OK , true);
        }
        return Helper.generateResponse("failed to send email , user with email "+keyDTO.getKey()+ "not found" , HttpStatus.NOT_FOUND , false);
    }

    @GetMapping("{token}")
    public ResponseEntity<Object> activationAccount(@PathVariable("token") String token){
        boolean activation = userServiceImpl.accountActivation(token);
        if(activation){
            return Helper.generateResponse(
                    "success activate your account please login to claim your jwt token"
                    , HttpStatus.OK
                    , true
            );
        }
        return Helper.generateResponse(
                "failed activate your account your token not valid"
                , HttpStatus.NOT_FOUND
                , false
        );
    }


}