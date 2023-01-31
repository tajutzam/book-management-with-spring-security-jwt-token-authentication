package com.zam.springsecurityjwt.service.impl;

import com.zam.springsecurityjwt.dto.LoginRequest;
import com.zam.springsecurityjwt.dto.LoginResponse;
import com.zam.springsecurityjwt.dto.RegisterRequest;
import com.zam.springsecurityjwt.dto.AuthResponse;
import com.zam.springsecurityjwt.entity.User;
import com.zam.springsecurityjwt.repo.UserRepository;
import com.zam.springsecurityjwt.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private EmailService emailService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User with username "+ username + " not found")
        );
    }

    public Optional<AuthResponse> register(RegisterRequest registerRequest){
        Optional<User> optionalUserByUsername = userRepository.findByUsername(registerRequest.getUsername());
        Optional<User> optionalUserByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if(optionalUserByEmail.isPresent() || optionalUserByUsername.isPresent()){
            return Optional.empty();
        }
        var user = User.builder().
                username(registerRequest.getUsername())
                        .name(registerRequest.getName())
                                .password(passwordEncoder.encode(registerRequest.getPassword()))
                                        .role(registerRequest.getRole()).
                address(registerRequest.getAddress())
                        .phoneNumber(registerRequest.getPhoneNumber()).
                email(registerRequest.getEmail())
                                .enabled(false).
                build();
        String token = jwtService.generateToken(user);
        user.setToken(token);
        userRepository.save(user);
        return Optional.ofNullable(AuthResponse.builder().
                status(true).
                build());
    }

    public LoginResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername() ,
                        loginRequest.getPassword()
                )
        );
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if(userOptional.isPresent()){
            var user = userOptional.get();
            String token = jwtService.generateToken(user);
            user.setToken(token);
            userRepository.save(user);
            return LoginResponse.builder().token(token).status(true).build();
        }
        throw new UsernameNotFoundException("user with username "+ loginRequest.getUsername() + " not found");
    }
    public boolean accountActivation(String token){
        return userRepository.findByToken(token)
                .map(userResponse -> {
                    userResponse.setEnabled(true);
                    userRepository.save(userResponse);
                    return true;
                }).orElse(false);
    }
    public boolean sendEmail(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            var user = optionalUser.get();
            emailService.sendEmail(user.getEmail() , "ACCOUNT ACTIVATION" , user.getToken());
            return true;
        }
        return false;
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

}
