package com.security.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.security.security.filters.JwtAuthenticationFilter;
import com.security.security.filters.JwtAuthorizationFilter;
import com.security.security.jwt.JwtUtils;
import com.security.service.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtAuthorizationFilter authorizationFilter;
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity , AuthenticationManager authenticationManager ) throws Exception{
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        return httpSecurity
                        .csrf(config -> config.disable())
                        .authorizeHttpRequests(auth->{
                            auth.antMatchers("/hello").permitAll();
                            auth.antMatchers("/createUser").permitAll();
                            auth.anyRequest().authenticated();
                        })
                        .sessionManagement(session ->{
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        })
                        .addFilter(jwtAuthenticationFilter)
                        .addFilterBefore(authorizationFilter,UsernamePasswordAuthenticationFilter.class)
                        .build();
    }
    @Bean
     public PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception{
            return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder)
                                .and()
                                .build();
    }
}
