package com.irctc.service;

import com.irctc.entities.UserEntity;
import com.irctc.repository.UserRepository;
import com.irctc.request.LoginRequest;
import com.irctc.request.SignUpRequest;
import com.irctc.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    public UserServiceImpl(BCryptPasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public GenericResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.findByEmailId(signUpRequest.getEmailId()).isPresent()) {
            return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                    "user already exist!!", null);
        }
        UserEntity user = userRepository.save(UserEntity.builder()
                .username(signUpRequest.getUsername())
                .hashedPassword(encoder.encode(signUpRequest.getPassword()))
                .emailId(signUpRequest.getEmailId())
                .build());
        System.out.println("==============here ================");
        return new GenericResponse(HttpStatus.OK.value(), "success", "User has signed up successfully!!", user);
    }

    @Override
    public GenericResponse login(LoginRequest loginRequest) {
        Optional<UserEntity> user = userRepository.findByEmailId(loginRequest.getEmailId());
        if (user.isEmpty()) {
            return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                    "user doesn't exist please sign up before login!!", null);
        }
        return encoder.matches(loginRequest.getPassword(), user.get().getHashedPassword())
                ? new GenericResponse(HttpStatus.OK.value(), "success", "User has logged in successfully!!",
                    user) : new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                "Please enter correct password!!", null);
    }

    @Override
    public GenericResponse fetchUserTickets(String emailId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailId(emailId);
        return optionalUserEntity.map(userEntity -> new GenericResponse(HttpStatus.OK.value(), "success",
                        "tickets retrieved successfully!!", userEntity.getBookedTickets()))
                .orElseGet(() -> new GenericResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                        "No ticket found!!", null));
    }

}
