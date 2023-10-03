package com.example.ishoporder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ishoporder.exception.ApiException;
import com.example.ishoporder.model.request.JwtRequest;
import com.example.ishoporder.model.request.RegistrationUserDto;
import com.example.ishoporder.model.request.UserDto;
import com.example.ishoporder.model.response.JwtResponse;
import com.example.ishoporder.service.AuthService;
import com.example.ishoporder.service.UserService;
import com.example.ishoporder.utils.JwtTokenUtils;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthService.class})
@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtils jwtTokenUtils;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link AuthService#createAuthToken(JwtRequest)}
     */
    @Test
    void testCreateAuthToken() throws AuthenticationException {
        when(userService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        when(jwtTokenUtils.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        JwtRequest authRequest = new JwtRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        ResponseEntity<?> actualCreateAuthTokenResult = authService.createAuthToken(authRequest);
        assertTrue(actualCreateAuthTokenResult.hasBody());
        assertTrue(actualCreateAuthTokenResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualCreateAuthTokenResult.getStatusCode());
        assertEquals("ABC123", ((JwtResponse) actualCreateAuthTokenResult.getBody()).getToken());
        verify(userService).loadUserByUsername(Mockito.<String>any());
        verify(jwtTokenUtils).generateToken(Mockito.<UserDetails>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    }

    /**
     * Method under test: {@link AuthService#createAuthToken(JwtRequest)}
     */
    @Test
    void testCreateAuthToken2() throws AuthenticationException {
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenThrow(new BadCredentialsException("Msg"));

        JwtRequest authRequest = new JwtRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        ResponseEntity<?> actualCreateAuthTokenResult = authService.createAuthToken(authRequest);
        assertTrue(actualCreateAuthTokenResult.hasBody());
        assertTrue(actualCreateAuthTokenResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.UNAUTHORIZED, actualCreateAuthTokenResult.getStatusCode());
        assertEquals("Wrong login and password",
                ((ApiException) actualCreateAuthTokenResult.getBody()).getErrorMessage());
        assertEquals(401, ((ApiException) actualCreateAuthTokenResult.getBody()).getStatusCode());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    }

    /**
     * Method under test: {@link AuthService#createNewUser(RegistrationUserDto)}
     */
    @Test
    void testCreateNewUser() {
        com.example.ishoporder.model.entity.User user = new com.example.ishoporder.model.entity.User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<com.example.ishoporder.model.entity.User> ofResult = Optional.of(user);
        when(userService.findByUsername(Mockito.<String>any())).thenReturn(ofResult);

        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setConfirmPassword("iloveyou");
        registrationUserDto.setEmail("jane.doe@example.org");
        registrationUserDto.setPassword("iloveyou");
        registrationUserDto.setUsername("janedoe");
        ResponseEntity<?> actualCreateNewUserResult = authService.createNewUser(registrationUserDto);
        assertTrue(actualCreateNewUserResult.hasBody());
        assertTrue(actualCreateNewUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualCreateNewUserResult.getStatusCode());
        assertEquals("User already exist", ((ApiException) actualCreateNewUserResult.getBody()).getErrorMessage());
        assertEquals(400, ((ApiException) actualCreateNewUserResult.getBody()).getStatusCode());
        verify(userService).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthService#createNewUser(RegistrationUserDto)}
     */
    @Test
    void testCreateNewUser2() {
        com.example.ishoporder.model.entity.User user = new com.example.ishoporder.model.entity.User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        when(userService.createNewUser(Mockito.<RegistrationUserDto>any())).thenReturn(user);
        Optional<com.example.ishoporder.model.entity.User> emptyResult = Optional.empty();
        when(userService.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);

        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setConfirmPassword("iloveyou");
        registrationUserDto.setEmail("jane.doe@example.org");
        registrationUserDto.setPassword("iloveyou");
        registrationUserDto.setUsername("janedoe");
        ResponseEntity<?> actualCreateNewUserResult = authService.createNewUser(registrationUserDto);
        assertTrue(actualCreateNewUserResult.hasBody());
        assertTrue(actualCreateNewUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualCreateNewUserResult.getStatusCode());
        assertEquals("jane.doe@example.org", ((UserDto) actualCreateNewUserResult.getBody()).getEmail());
        assertEquals(1L, ((UserDto) actualCreateNewUserResult.getBody()).getId().longValue());
        assertEquals("janedoe", ((UserDto) actualCreateNewUserResult.getBody()).getUsername());
        verify(userService).createNewUser(Mockito.<RegistrationUserDto>any());
        verify(userService).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthService#createNewUser(RegistrationUserDto)}
     */
    @Test
    void testCreateNewUser3() {
        RegistrationUserDto registrationUserDto = mock(RegistrationUserDto.class);
        when(registrationUserDto.getUsername()).thenThrow(new BadCredentialsException("Msg"));
        when(registrationUserDto.getConfirmPassword()).thenReturn("iloveyou");
        when(registrationUserDto.getPassword()).thenReturn("iloveyou");
        doNothing().when(registrationUserDto).setConfirmPassword(Mockito.<String>any());
        doNothing().when(registrationUserDto).setEmail(Mockito.<String>any());
        doNothing().when(registrationUserDto).setPassword(Mockito.<String>any());
        doNothing().when(registrationUserDto).setUsername(Mockito.<String>any());
        registrationUserDto.setConfirmPassword("iloveyou");
        registrationUserDto.setEmail("jane.doe@example.org");
        registrationUserDto.setPassword("iloveyou");
        registrationUserDto.setUsername("janedoe");
        assertThrows(BadCredentialsException.class, () -> authService.createNewUser(registrationUserDto));
        verify(registrationUserDto).getConfirmPassword();
        verify(registrationUserDto).getPassword();
        verify(registrationUserDto).getUsername();
        verify(registrationUserDto).setConfirmPassword(Mockito.<String>any());
        verify(registrationUserDto).setEmail(Mockito.<String>any());
        verify(registrationUserDto).setPassword(Mockito.<String>any());
        verify(registrationUserDto).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthService#createNewUser(RegistrationUserDto)}
     */
    @Test
    void testCreateNewUser4() {
        RegistrationUserDto registrationUserDto = mock(RegistrationUserDto.class);
        when(registrationUserDto.getConfirmPassword()).thenReturn("foo");
        when(registrationUserDto.getPassword()).thenReturn("iloveyou");
        doNothing().when(registrationUserDto).setConfirmPassword(Mockito.<String>any());
        doNothing().when(registrationUserDto).setEmail(Mockito.<String>any());
        doNothing().when(registrationUserDto).setPassword(Mockito.<String>any());
        doNothing().when(registrationUserDto).setUsername(Mockito.<String>any());
        registrationUserDto.setConfirmPassword("iloveyou");
        registrationUserDto.setEmail("jane.doe@example.org");
        registrationUserDto.setPassword("iloveyou");
        registrationUserDto.setUsername("janedoe");
        ResponseEntity<?> actualCreateNewUserResult = authService.createNewUser(registrationUserDto);
        assertTrue(actualCreateNewUserResult.hasBody());
        assertTrue(actualCreateNewUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualCreateNewUserResult.getStatusCode());
        assertEquals("Passwords didn't match", ((ApiException) actualCreateNewUserResult.getBody()).getErrorMessage());
        assertEquals(400, ((ApiException) actualCreateNewUserResult.getBody()).getStatusCode());
        verify(registrationUserDto).getConfirmPassword();
        verify(registrationUserDto).getPassword();
        verify(registrationUserDto).setConfirmPassword(Mockito.<String>any());
        verify(registrationUserDto).setEmail(Mockito.<String>any());
        verify(registrationUserDto).setPassword(Mockito.<String>any());
        verify(registrationUserDto).setUsername(Mockito.<String>any());
    }
}

