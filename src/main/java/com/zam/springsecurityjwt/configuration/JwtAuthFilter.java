package com.zam.springsecurityjwt.configuration;


import com.zam.springsecurityjwt.service.impl.JwtServiceImpl;
import com.zam.springsecurityjwt.entity.User;
import com.zam.springsecurityjwt.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private  final JwtServiceImpl jwtService;
    private final UserServiceImpl userServiceImpl;
    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain) throws ServletException, IOException {
          final String authHeader = request.getHeader("Authorization");
          final String jwtToken;
          if(authHeader == null || !authHeader.startsWith("Bearer ")){
              filterChain.doFilter(request , response);
              return;
          }
          jwtToken = authHeader.substring(7);
          final String username = jwtService.extractUsername(jwtToken);
          if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null){
                    User userDetails = userServiceImpl.loadUserByUsername(username);
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
