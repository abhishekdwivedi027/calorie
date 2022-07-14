package com.tt.calorie.common.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;

    private Long userId;

    private boolean isAdmin;

    private List<? extends GrantedAuthority> grantedAuthorities;
}
