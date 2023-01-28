package com.zam.springsecurityjwt.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
public class LoginRequest {

    private String username;
    private String password;

}
