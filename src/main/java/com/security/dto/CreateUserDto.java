package com.security.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;
    private String password;
    private Set<String> roles;
}
