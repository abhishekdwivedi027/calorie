package com.tt.calorie.common;

import com.tt.calorie.common.authorization.Permission;
import com.tt.calorie.common.authorization.Role;
import com.tt.calorie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userService.getUserByCredentials(username, null) != null) {
            Role role = userService.getUserByCredentials(username, null).getRole();
            List<Permission> permissions = Role.getPermissions(role);
            List<? extends GrantedAuthority> authorities =
                    permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toList());
            return new User(userService.getUserByCredentials(username, null).getUsername(), userService.getUserByCredentials(username, null).getPassword(), authorities);
        } else {
            return null;
        }
    }


}
