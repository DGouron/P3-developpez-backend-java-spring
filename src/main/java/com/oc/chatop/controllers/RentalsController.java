package com.oc.chatop.controllers;

import com.oc.chatop.dtos.RentalRequestDTO;
import com.oc.chatop.dtos.RentalResponseDTO;
import com.oc.chatop.entities.Rental;
import com.oc.chatop.services.RentalService;
import com.oc.chatop.utils.RentalMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "rentals")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rental = rentalService.findRentalById(Long.valueOf(id));
        return rental.map(r -> ResponseEntity.ok(rentalMapper.toRentalResponseDTO(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RentalResponseDTO>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        List<RentalResponseDTO> rentalResponseDTOs = rentals.stream()
                .map(rentalMapper::toRentalResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalResponseDTOs);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RentalResponseDTO> createRental(
            @Valid @ModelAttribute RentalRequestDTO rentalRequestDTO) {

        Rental rental = rentalMapper.toRental(rentalRequestDTO);
        Rental savedRental = rentalService.saveRental(rental);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalMapper.toRentalResponseDTO(savedRental));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RentalResponseDTO> updateRental(
            @PathVariable Integer id,
            @Valid @ModelAttribute RentalRequestDTO rentalRequestDTO) {

        Optional<Rental> existingRental = rentalService.findRentalById(Long.valueOf(id));
        if (existingRental.isPresent()) {
            Rental rental = rentalMapper.toRental(rentalRequestDTO);
            rental.setId(id);
            Rental updatedRental = rentalService.saveRental(rental);
            return ResponseEntity.ok(rentalMapper.toRentalResponseDTO(updatedRental));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}