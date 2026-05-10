package com.irctc.controller;

import com.irctc.request.LoginRequest;
import com.irctc.response.GenericResponse;
import com.irctc.service.JwtServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private JwtServiceUtil jwtService;
    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping("/generate-token")
    public ResponseEntity<GenericResponse> generateToken(@RequestBody LoginRequest loginRequest) {

        if (this.doAuthenticate(loginRequest.getEmailId(), loginRequest.getPassword())) {

            String token = jwtService.generateToken(loginRequest.getEmailId());
            Map<String, Object> data = Collections.singletonMap("jwt-token", token);

            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), "success",
                    "Token has been generated successfully", data));
        }

        return ResponseEntity.ok(new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", "Authentication Failed!!", null));
    }

    private boolean doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authentication);
        return authenticate.isAuthenticated();
    }

}
