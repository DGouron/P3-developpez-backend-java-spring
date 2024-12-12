package com.oc.chatop.utils;

import com.oc.chatop.dtos.UserRequestDTO;
import com.oc.chatop.dtos.UserResponseDTO;
import com.oc.chatop.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setUpdatedAt(user.getUpdatedAt());
        return userResponseDTO;
    }
}