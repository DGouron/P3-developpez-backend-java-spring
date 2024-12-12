package com.oc.chatop.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class RentalRequestDTO {
    @NotNull(message = "Owner ID is required")
    private Integer ownerId;
    private String name;
    private Integer surface;
    private String picture;
    private String description;
    private Double price;
}