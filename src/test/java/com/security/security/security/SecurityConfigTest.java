package com.security.security.security;

import com.security.security.SecurityConfig;
import com.security.security.filters.JwtAuthorizationFilter;
import com.security.security.jwt.JwtUtils;
import com.security.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SecurityConfigTest {
    @InjectMocks
    SecurityConfig securityConfig;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private JwtAuthorizationFilter authorizationFilter;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private HttpSecurity httpSecurity;
    @BeforeEach
    public void setUp() {
        // Puedes inicializar o configurar objetos adicionales aquí si es necesario
    }

    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        when(httpSecurity.getSharedObject(AuthenticationManager.class)).thenReturn(authenticationManager);
        AuthenticationManager manager = securityConfig.authenticationManager(httpSecurity, new BCryptPasswordEncoder());
        assertNotNull(manager);
    }

    @Test
    @WithMockUser
    public void testSecurityFilterChain() throws Exception {
        SecurityFilterChain filterChain = securityConfig.securityFilterChain(httpSecurity, authenticationManager);
        assertNotNull(filterChain);
        // Verifica que las configuraciones específicas del HttpSecurity se apliquen correctamente
        verify(httpSecurity).csrf(any());
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).sessionManagement(any());
    }



}
