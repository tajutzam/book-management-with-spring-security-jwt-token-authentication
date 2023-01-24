package com.zam.springsecurityjwt.service;

import com.zam.springsecurityjwt.auth.JwtService;
import com.zam.springsecurityjwt.auth.requst.RegisterRequest;
import com.zam.springsecurityjwt.auth.response.AuthResponse;
import com.zam.springsecurityjwt.entity.User;
import com.zam.springsecurityjwt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtService jwtService;
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User with username "+ username + " not found")
        );
    }

    public AuthResponse register(RegisterRequest registerRequest){
        System.out.println(registerRequest.getPassword());
        System.out.println(registerRequest.getRole());
        var user = User.builder().
                username(registerRequest.getUsername())
                        .name(registerRequest.getName())
                                .password(passwordEncoder.encode(registerRequest.getPassword()))
                                        .role(registerRequest.getRole()).
                build();
        String token = jwtService.generateToken(user);
        userRepository.save(user);
        return AuthResponse.builder().
                token(token).
                build();
    }
}
