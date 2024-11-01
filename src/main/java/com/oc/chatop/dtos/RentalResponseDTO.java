package com.oc.chatop.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RentalResponseDTO {
    private Integer id;
    private String name;
    private Integer surface;
    private String picture;
    private String description;
    private Integer ownerId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Double price;
}
