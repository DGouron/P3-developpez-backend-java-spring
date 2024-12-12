package com.oc.chatop.utils;

import com.oc.chatop.dtos.RentalRequestDTO;
import com.oc.chatop.dtos.RentalResponseDTO;
import com.oc.chatop.entities.Rental;
import com.oc.chatop.entities.User;
import com.oc.chatop.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    private final UserService userService;

    public RentalMapper(UserService userService) {
        this.userService = userService;
    }

    public Rental toRental(RentalRequestDTO rentalRequestDTO) {
        Rental rental = new Rental();
        rental.setName(rentalRequestDTO.getName());
        rental.setSurface(rentalRequestDTO.getSurface());
        rental.setPicture(rentalRequestDTO.getPicture());
        rental.setDescription(rentalRequestDTO.getDescription());
        rental.setPrice(rentalRequestDTO.getPrice());

        if (rentalRequestDTO.getOwnerId() == null) {
            throw new RuntimeException("Owner ID is required");
        }

        Long ownerId = rentalRequestDTO.getOwnerId().longValue();
        User owner = userService.findUserById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        rental.setOwner(owner);

        return rental;
    }

    public RentalResponseDTO toRentalResponseDTO(Rental rental) {
        RentalResponseDTO rentalResponseDTO = new RentalResponseDTO();
        rentalResponseDTO.setId(rental.getId());
        rentalResponseDTO.setName(rental.getName());
        rentalResponseDTO.setSurface(rental.getSurface());
        rentalResponseDTO.setPicture(rental.getPicture());
        rentalResponseDTO.setDescription(rental.getDescription());
        rentalResponseDTO.setPrice(rental.getPrice());
        rentalResponseDTO.setOwnerId(rental.getOwner().getId());
        rentalResponseDTO.setCreatedAt(rental.getCreatedAt());
        rentalResponseDTO.setUpdatedAt(rental.getUpdatedAt());
        return rentalResponseDTO;
    }
}