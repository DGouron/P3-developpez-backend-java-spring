package com.oc.chatop.dtos;

import lombok.Data;

@Data
public class MessageResponseDTO {

    private Integer id;
    private String message;
    private Integer userId;
    private Integer rentalId;
}