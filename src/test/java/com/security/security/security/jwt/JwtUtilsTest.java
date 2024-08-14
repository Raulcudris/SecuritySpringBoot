package com.security.security.security.jwt;
import com.security.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilsTest {
    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        // Configurar valores de las propiedades secretKey y timeExpiration
        ReflectionTestUtils.setField(jwtUtils, "secretKey", "mysecretkeymysecretkeymysecretkeymysecretkey");
        ReflectionTestUtils.setField(jwtUtils, "timeExpiration", "3600000"); // 1 hora en milisegundos
    }

    @Test
    public void testGenerateAccesToken() {
        String username = "testuser";
        String token = jwtUtils.generateAccesToken(username);

        assertNotNull(token);
        assertTrue(jwtUtils.isTokenValid(token));

        String extractedUsername = jwtUtils.getUsernameFromToken(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testIsTokenValid_ValidToken() {
        String username = "testuser";
        String token = jwtUtils.generateAccesToken(username);

        assertTrue(jwtUtils.isTokenValid(token));
    }

    @Test
    public void testIsTokenValid_InvalidToken() {
        String invalidToken = "invalidtoken";

        assertFalse(jwtUtils.isTokenValid(invalidToken));
    }

    @Test
    public void testGetUsernameFromToken() {
        String username = "testuser";
        String token = jwtUtils.generateAccesToken(username);

        String extractedUsername = jwtUtils.getUsernameFromToken(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testExtractAllClaims() {
        String username = "testuser";
        String token = jwtUtils.generateAccesToken(username);

        Claims claims = jwtUtils.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals(username, claims.getSubject());
    }

    @Test
    public void testGetSignatureKey() {
        SecretKey key = jwtUtils.getSignatureKey();
        assertNotNull(key);
        assertEquals(Keys.hmacShaKeyFor("mysecretkeymysecretkeymysecretkeymysecretkey".getBytes()).toString(), key.toString());
    }


}
