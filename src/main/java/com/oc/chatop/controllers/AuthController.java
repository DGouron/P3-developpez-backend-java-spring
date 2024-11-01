package com.oc.chatop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="auth")
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("me")
    public String getMe(){
        return "hello";
    }

    /*Créer des DTO qui correspondent à tout ce qui est dans request body : Request, RegisterResponse */
    public String postRegister(@RequestBody User user){
        return "hello";
    }
}