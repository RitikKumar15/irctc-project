package com.irctc.controller;

import com.irctc.request.LoginRequest;
import com.irctc.request.SignUpRequest;
import com.irctc.response.GenericResponse;
import com.irctc.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> login(@RequestBody LoginRequest loginRequest) {
        return  ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping(value = "/fetchTickets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> fetchUserTickets(@RequestParam String emailId) {
        return ResponseEntity.ok(userService.fetchUserTickets(emailId));
    }
}
