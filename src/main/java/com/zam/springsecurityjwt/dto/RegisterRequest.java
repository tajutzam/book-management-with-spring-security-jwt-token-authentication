package com.zam.springsecurityjwt.dto;


import com.zam.springsecurityjwt.util.enumclass.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterRequest {

    private String name;
    private String username;
    private String password;
    private Role role;
    private String email;
    private String address;
    private int phoneNumber;

}
