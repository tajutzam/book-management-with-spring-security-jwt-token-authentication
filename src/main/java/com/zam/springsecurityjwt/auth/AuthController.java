package com.zam.springsecurityjwt.auth;


import com.zam.springsecurityjwt.auth.requst.LoginRequest;
import com.zam.springsecurityjwt.auth.requst.RegisterRequest;
import com.zam.springsecurityjwt.auth.response.AuthResponse;
import com.zam.springsecurityjwt.service.UserService;
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
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody  RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        try {
            AuthResponse authResponse = userService.login(loginRequest);
            return ResponseEntity.ok(authResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
