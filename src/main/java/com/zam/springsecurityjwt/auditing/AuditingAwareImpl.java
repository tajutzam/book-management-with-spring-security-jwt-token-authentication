package com.zam.springsecurityjwt.auditing;

import com.zam.springsecurityjwt.auth.JwtService;
import com.zam.springsecurityjwt.entity.User;
import com.zam.springsecurityjwt.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class AuditingAwareImpl  implements AuditorAware<String> {

    @Autowired
    private JwtService jwtService;

    @Override
    public Optional<String> getCurrentAuditor() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(user.getName());
    }
}
