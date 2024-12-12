package com.oc.chatop.dtos;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String name;
    private String password;
}