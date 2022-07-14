package com.tt.calorie.common.authentication;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;
}
