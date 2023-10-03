package com.example.ishoporder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ishoporder.model.entity.Role;
import com.example.ishoporder.model.entity.User;
import com.example.ishoporder.model.request.RegistrationUserDto;
import com.example.ishoporder.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#findByUsername(String)}
     */
    @Test
    void testFindByUsername() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        Optional<User> actualFindByUsernameResult = userService.findByUsername("janedoe");
        assertSame(ofResult, actualFindByUsernameResult);
        assertTrue(actualFindByUsernameResult.isPresent());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#findByUsername(String)}
     */
    @Test
    void testFindByUsername2() {
        when(userRepository.findByUsername(Mockito.<String>any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = userService.loadUserByUsername("janedoe");
        assertTrue(actualLoadUserByUsernameResult.getAuthorities().isEmpty());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        Role role = new Role();
        role.setId(1);
        role.setName("Name");

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role);
        User user = mock(User.class);
        when(user.getPassword()).thenReturn("iloveyou");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getRoles()).thenReturn(roleList);
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setRoles(Mockito.<Collection<Role>>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = userService.loadUserByUsername("janedoe");
        assertEquals(1, actualLoadUserByUsernameResult.getAuthorities().size());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user).getPassword();
        verify(user).getUsername();
        verify(user).getRoles();
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setRoles(Mockito.<Collection<Role>>any());
        verify(user).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        Role role = new Role();
        role.setId(1);
        role.setName("Name");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("com.example.ishoporder.model.entity.Role");

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role2);
        roleList.add(role);
        User user = mock(User.class);
        when(user.getPassword()).thenReturn("iloveyou");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getRoles()).thenReturn(roleList);
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setRoles(Mockito.<Collection<Role>>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = userService.loadUserByUsername("janedoe");
        assertEquals(2, actualLoadUserByUsernameResult.getAuthorities().size());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user).getPassword();
        verify(user).getUsername();
        verify(user).getRoles();
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setRoles(Mockito.<Collection<Role>>any());
        verify(user).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername4() throws UsernameNotFoundException {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#createNewUser(RegistrationUserDto)}
     */
    @Test
    void testCreateNewUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        Role role = new Role();
        role.setId(1);
        role.setName("Name");
        when(roleService.getUserRole()).thenReturn(role);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setConfirmPassword("iloveyou");
        registrationUserDto.setEmail("jane.doe@example.org");
        registrationUserDto.setPassword("iloveyou");
        registrationUserDto.setUsername("janedoe");
        assertSame(user, userService.createNewUser(registrationUserDto));
        verify(userRepository).save(Mockito.<User>any());
        verify(roleService).getUserRole();
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link UserService#createNewUser(RegistrationUserDto)}
     */
    @Test
    void testCreateNewUser2() {
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenThrow(new UsernameNotFoundException("Msg"));

        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setConfirmPassword("iloveyou");
        registrationUserDto.setEmail("jane.doe@example.org");
        registrationUserDto.setPassword("iloveyou");
        registrationUserDto.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class, () -> userService.createNewUser(registrationUserDto));
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }
}

