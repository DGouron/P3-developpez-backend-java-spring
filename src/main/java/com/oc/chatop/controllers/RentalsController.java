package com.oc.chatop.controllers;

import com.oc.chatop.dtos.RentalRequestDTO;
import com.oc.chatop.dtos.RentalResponseDTO;
import com.oc.chatop.models.Rental;
import com.oc.chatop.services.RentalService;
import com.oc.chatop.utils.RentalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "rentals")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    @GetMapping
    public List<RentalResponseDTO> getRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return rentals.stream()
                .map(rentalMapper::toRentalResponseDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<RentalResponseDTO> createRental(@RequestBody RentalRequestDTO rentalRequestDTO) {
        Rental rental = rentalMapper.toRental(rentalRequestDTO);
        Rental savedRental = rentalService.saveRental(rental);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalMapper.toRentalResponseDTO(savedRental));
    }
}