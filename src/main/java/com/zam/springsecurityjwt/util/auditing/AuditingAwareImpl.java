package com.zam.springsecurityjwt.util.auditing;

import com.zam.springsecurityjwt.service.impl.JwtServiceImpl;
import com.zam.springsecurityjwt.exeptions.ApiRequestException;
import com.zam.springsecurityjwt.repo.UserRepository;
import com.zam.springsecurityjwt.util.Helper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditingAwareImpl  implements AuditorAware<String> {

    @Autowired
    private JwtServiceImpl jwtService;

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
