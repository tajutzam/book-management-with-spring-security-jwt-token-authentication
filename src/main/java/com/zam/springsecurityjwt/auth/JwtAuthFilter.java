package com.zam.springsecurityjwt.auth;


import com.zam.springsecurityjwt.entity.User;
import com.zam.springsecurityjwt.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain) throws ServletException, IOException {
        // get token from header
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        // check if header null or header not stat with bearer and stop
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
           filterChain.doFilter(request , response);
            return;
        }
        jwtToken = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwtToken);
            if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null){
                User userDetails = userService.loadUserByUsername(username);
                if(jwtService.isTokenValid(jwtToken , userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails ,
                            null ,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        filterChain.doFilter(request , response);
    }
}
