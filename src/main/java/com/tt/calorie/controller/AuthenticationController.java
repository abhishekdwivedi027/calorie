package com.tt.calorie.controller;

import com.tt.calorie.common.CustomUserDetailsService;
import com.tt.calorie.common.authentication.AuthenticationRequest;
import com.tt.calorie.common.authentication.AuthenticationResponse;
import com.tt.calorie.common.authorization.Role;
import com.tt.calorie.model.User;
import com.tt.calorie.service.AuthenticationService;
import com.tt.calorie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tokens")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse createToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String token = authenticationService.generateToken(userDetails);

        User user = userService.getUserByCredentials(authenticationRequest.getUsername(), null);
        user.setToken(token);
        user = userService.updateUser(user);
        boolean isAdmin  = user.getRole() == Role.ADMIN;

        return new AuthenticationResponse(token, user.getId(), isAdmin, new ArrayList<>(userDetails.getAuthorities()));
    }
}
