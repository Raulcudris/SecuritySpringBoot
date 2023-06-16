package com.security.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

     
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity ) throws Exception{
        return httpSecurity
                        //55:52 https://www.youtube.com/watch?v=aeCotM2DORo
                    .build();
    }
    
}
