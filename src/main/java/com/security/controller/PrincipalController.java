package com.security.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.CreateUserDto;
import com.security.models.ERole;
import com.security.models.RoleEntity;
import com.security.models.UserEntity;
import com.security.repositories.UserRepository;

@RestController
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    
    @GetMapping("/hello")
    public String hello(){
        return "Hello world Not Secured";
    }


     @GetMapping("/helloSecured")
    public String helloSecured(){
        return "Hello world Secured";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto createUserDto){
        
        Set<RoleEntity> roles = createUserDto.getRoles().stream()
                        .map(role -> RoleEntity.builder()
                                    .name(ERole.valueOf(role))
                                    .build())
                        .collect(Collectors.toSet());               


        UserEntity userEntity = UserEntity.builder()
                    .username(createUserDto.getUsername())
                    .password(passwordEncoder.encode(createUserDto.getPassword()))
                    .email(createUserDto.getEmail())
                    .roles(roles)
                    .build();
        
        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }


    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Ok";
    }


}
