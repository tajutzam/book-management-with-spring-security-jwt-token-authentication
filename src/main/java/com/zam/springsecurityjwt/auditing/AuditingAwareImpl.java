package com.zam.springsecurityjwt.auditing;

import com.zam.springsecurityjwt.auth.JwtService;
import com.zam.springsecurityjwt.entity.User;
import com.zam.springsecurityjwt.exeptions.ApiRequestException;
import com.zam.springsecurityjwt.repo.UserRepository;
import com.zam.springsecurityjwt.util.Helper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class AuditingAwareImpl  implements AuditorAware<String> {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Optional<String> getCurrentAuditor() {
        String tokenHeader = Helper.getBearerTokenHeader();
        if(tokenHeader!=null){
            String token = tokenHeader.substring(7);
            try{
                String username = jwtService.getSingleClaim(token, Claims::getSubject);
                return Optional.of(username);
            }catch (RuntimeException e){
                throw new ApiRequestException("token not valid");
            }
        }
        return Optional.empty();
    }
}
