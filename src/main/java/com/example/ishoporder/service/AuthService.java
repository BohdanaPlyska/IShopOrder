package com.example.ishoporder.service;

import com.example.ishoporder.exception.ApiException;
import com.example.ishoporder.model.entity.User;
import com.example.ishoporder.model.request.JwtRequest;
import com.example.ishoporder.model.request.RegistrationUserDto;
import com.example.ishoporder.model.request.UserDto;
import com.example.ishoporder.model.response.JwtResponse;
import com.example.ishoporder.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZonedDateTime;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiException(HttpStatus.UNAUTHORIZED.value(), "Wrong login and password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new ApiException(HttpStatus.BAD_REQUEST.value(), "Passwords didn't match", ZonedDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new ApiException(HttpStatus.BAD_REQUEST.value(), "User already exist",ZonedDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}