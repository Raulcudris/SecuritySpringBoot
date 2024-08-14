package com.security.security.security.filters;
import com.security.security.filters.JwtAuthenticationFilter;
import com.security.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtAuthenticationFilterTest {
    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private Authentication authResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
    }

    @Test
    public void testAttemptAuthentication() throws Exception {
        String jsonUser = "{\"username\":\"testuser\",\"password\":\"testpass\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonUser.getBytes(StandardCharsets.UTF_8));

        when(request.getInputStream()).thenReturn((ServletInputStream) inputStream);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authResult);

        Authentication result = jwtAuthenticationFilter.attemptAuthentication(request, response);

        assertNotNull(result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testSuccessfulAuthentication() throws Exception {
        User user = new User("testuser", "testpass", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(authResult.getPrincipal()).thenReturn(user);
        when(jwtUtils.generateAccesToken("testuser")).thenReturn("testtoken");

        jwtAuthenticationFilter.successfulAuthentication(request, response, chain, authResult);

        verify(response).addHeader("Authorization", "testtoken");
        verify(response).setStatus(HttpStatus.OK.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

}
