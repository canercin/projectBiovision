package com.biovision.back.security;

import com.biovision.back.entity.JwtUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static JwtUserDetails getCurrentUser() {
        return (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
