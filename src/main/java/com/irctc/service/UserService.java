package com.irctc.service;

import com.irctc.request.LoginRequest;
import com.irctc.request.SignUpRequest;
import com.irctc.response.GenericResponse;

public interface UserService {
    GenericResponse signUp(SignUpRequest signUpRequest);
    GenericResponse login(LoginRequest loginRequest);
    GenericResponse fetchUserTickets(String emailId);
}
