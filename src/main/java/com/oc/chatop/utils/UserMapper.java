package com.oc.chatop.utils;

import com.oc.chatop.dtos.UserRegisterRequestDTO;
import com.oc.chatop.dtos.UserResponseDTO;
import com.oc.chatop.models.User;

public class UserMapper {

    public static User toUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        return User.builder()
                .name(userRegisterRequestDTO.getName())
                .email(userRegisterRequestDTO.getEmail())
                .password(userRegisterRequestDTO.getPassword())
                .build();
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setUpdatedAt(user.getUpdatedAt());
        return userResponseDTO;
    }
}