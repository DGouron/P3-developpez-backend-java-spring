package com.oc.chatop.dtos;

import lombok.Data;

@Data
public class UserRegisterRequestDTO {
    private String name;
    private String email;
    private String password;
}
