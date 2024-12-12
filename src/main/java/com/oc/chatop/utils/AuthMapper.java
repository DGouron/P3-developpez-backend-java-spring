package com.oc.chatop.utils;

import com.oc.chatop.dtos.AuthRequestDTO;
import com.oc.chatop.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User toUser(AuthRequestDTO authRequestDTO) {
        User user = new User();
        user.setEmail(authRequestDTO.getEmail());
        user.setPassword(authRequestDTO.getPassword());
        user.setName(authRequestDTO.getName());
        return user;
    }
}