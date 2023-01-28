package com.zam.springsecurityjwt.service.impl;

import com.zam.springsecurityjwt.dto.LoginRequest;
import com.zam.springsecurityjwt.dto.RegisterRequest;
import com.zam.springsecurityjwt.dto.AuthResponse;
import com.zam.springsecurityjwt.entity.User;
import com.zam.springsecurityjwt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    @Lazy
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    @Lazy
    private JwtServiceImpl jwtService;

    @Autowired
    @Lazy
    private  AuthenticationManager authenticationManager;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User with username "+ username + " not found")
        );
    }

    public Optional<AuthResponse> register(RegisterRequest registerRequest){
        System.out.println(registerRequest.getPassword());
        System.out.println(registerRequest.getRole());
        Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());
        if(userOptional.isPresent()){
            return Optional.empty();
        }
        var user = User.builder().
                username(registerRequest.getUsername())
                        .name(registerRequest.getName())
                                .password(passwordEncoder.encode(registerRequest.getPassword()))
                                        .role(registerRequest.getRole()).
                build();
        String token = jwtService.generateToken(user);
        userRepository.save(user);
        return Optional.ofNullable(AuthResponse.builder().
                token(token).
                build());
    }

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername() ,
                        loginRequest.getPassword()
                )
        );
        var user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with email not fond"));
        String token = jwtService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }


}
