package com.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRolesController {
    
   @GetMapping("/accessAdmin")
    public String accessAdmin(){
        return "Hola, has accedito con rol de ADMIN";
    }

    @GetMapping("/accessUser")
    public String accessUser(){
        return "Hola, has accedito con rol de USER";
    }

    @GetMapping("/accessInvited")
    public String accessInvited(){
        return "Hola, has accedito con rol de INVITED";
    }

}
