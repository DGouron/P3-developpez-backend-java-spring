package com.oc.chatop.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequestDTO {

    private String message;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("rental_id")
    private Integer rentalId;
}