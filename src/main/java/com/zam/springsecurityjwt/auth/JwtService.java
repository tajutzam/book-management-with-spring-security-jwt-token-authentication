package com.zam.springsecurityjwt.auth;

import com.zam.springsecurityjwt.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private final String SECRET_KEY = "294A404E635266556A586E327235753778214125442A472D4B6150645367566B";

    public String extractUsername(String token){
        return getSingleClaim(token , Claims::getSubject);
    }

    public  <T> T getSingleClaim(String token , Function<Claims ,T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Key getSigningKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }
    public String generateToken(Map<String , Object> payload , User user){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        return Jwts.builder().
                setClaims(payload)
                .setSubject(user.getUsername())
                .claim("name" , user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(dt)
                .signWith(getSigningKey() , SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(User user){
        return generateToken(new HashMap<>() , user);
    }

    public boolean isTokenValid(String token   , User user){
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return getSingleClaim(token, Claims::getExpiration);
    }


}
