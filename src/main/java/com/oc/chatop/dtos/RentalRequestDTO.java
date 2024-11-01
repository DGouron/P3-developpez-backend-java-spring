package com.oc.chatop.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalRequestDTO {
    private String name;
    private Integer surface;
    private String picture;
    private String description;
    private Integer ownerId;
    private Double price;
}