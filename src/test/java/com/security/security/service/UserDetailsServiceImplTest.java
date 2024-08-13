package com.security.security.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import com.security.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.boot.test.context.SpringBootTest;

import com.security.models.UserEntity;
import com.security.models.RoleEntity;
import com.security.models.ERole;
import com.security.repositories.UserRepository;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        // Crear roles de ejemplo
        RoleEntity roleUser = new RoleEntity();
        roleUser.setName(ERole.USER);

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleUser);

        // Crear un UserEntity de ejemplo
        userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("password123");
        userEntity.setRoles(roles);
    }
    @Test
    public void testLoadUserByUsername_UserExists() {
        // Simular que el usuario existe en el repositorio
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

        // Ejecutar el método que se va a probar
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("testuser");

        // Verificar que los detalles del usuario son correctos
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLEUSER")));
    }
    @Test
    public void testLoadUserByUsername_UserDoesNotExist() {
        // Simular que el usuario no existe en el repositorio
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        // Ejecutar el método y esperar una excepción
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername("nonexistentuser");
        });
    }
}