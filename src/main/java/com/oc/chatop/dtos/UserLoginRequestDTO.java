package com.oc.chatop.dtos;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
    private String email;
    private String password;
}