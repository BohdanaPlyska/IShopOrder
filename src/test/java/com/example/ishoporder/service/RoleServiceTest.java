package com.example.ishoporder.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ishoporder.model.entity.Role;
import com.example.ishoporder.repository.RoleRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RoleService.class})
@ExtendWith(SpringExtension.class)
class RoleServiceTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    /**
     * Method under test: {@link RoleService#getUserRole()}
     */
    @Test
    void testGetUserRole() {
        Role role = new Role();
        role.setId(1);
        role.setName("Name");
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
        assertSame(role, roleService.getUserRole());
        verify(roleRepository).findByName(Mockito.<String>any());
    }
}

