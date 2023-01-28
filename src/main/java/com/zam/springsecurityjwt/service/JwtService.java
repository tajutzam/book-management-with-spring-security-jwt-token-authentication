package com.zam.springsecurityjwt.service;

import com.zam.springsecurityjwt.entity.User;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    public String extractUsername(String token);
    public String generateToken(User user);
    public  <T> T getSingleClaim(String token , Function<Claims,T> claimResolver);
    public Claims extractAllClaims(String token);
    public Key getSigningKey();
    public String generateToken(Map<String , Object> payload , User user);
    public boolean isTokenValid(String token   , User user);
    public boolean isTokenExpired(String token);
    public Date extractExpiration(String token);

}
