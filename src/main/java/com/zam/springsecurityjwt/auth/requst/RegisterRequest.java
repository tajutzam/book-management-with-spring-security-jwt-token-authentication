package com.zam.springsecurityjwt.auth.requst;


import com.zam.springsecurityjwt.enumclass.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterRequest {

    private String name;
    private String username;
    private String password;
    private Role role;
}
