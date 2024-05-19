package com.example.onlinebankingapp.components;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.onlinebankingapp.entities.CustomerEntity;
public class SecurityUtils {
    public CustomerEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof CustomerEntity selectedUser) {
            if(!selectedUser.isActive()) {
                return null;
            }
            return (CustomerEntity) authentication.getPrincipal();
        }
        return null;
    }

}
