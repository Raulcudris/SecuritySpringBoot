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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        PasswordEncoder passwordEncoder = securityConfig.pas
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }


}
