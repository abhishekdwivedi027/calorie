package com.tt.calorie.service;

import com.tt.calorie.common.authorization.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    private UserService userService;

    public boolean isUserNotPermitted(Long userId, Permission permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        boolean ownEntryOnly = principal.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equalsIgnoreCase(permission.name()));
        if (!ownEntryOnly) {
            return false;
        }
        com.tt.calorie.model.User user = userService.getUser(userId);
        String username = principal.getUsername();
        String password = principal.getPassword();
        return !user.getUsername().equals(username) || !user.getPassword().equals(password);
    }
}
