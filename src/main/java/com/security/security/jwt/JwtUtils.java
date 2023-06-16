package com.security.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
    private String secretKey = "7639848111F24212121B445421S545125D4212S5451A51115155151100000055051";
    private String timeExpiration ="604800";


    //Generar token de acceso
    public String generateAccesToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(timeExpiration)))
                .signWith((java.security.Key) getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //Validar el token de acceso
    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return true;
        } 
         catch (Exception e) {
            log.error("Token invalido, error".concat(e.getMessage()));
            return false;
        }
    }

    //Obtener el username del token
    public String getUsernameFromToken(String token){
        return getClaim(token,Claims::getSubject);
    }



    //Obtener un solo claim
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction ){
       Claims claims = extractAllClaims(token);
       return claimsTFunction.apply(claims);
    }


    //Obtener todos los claims del token
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }


    //Obtener firma del token
    public SecretKey getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    
}
