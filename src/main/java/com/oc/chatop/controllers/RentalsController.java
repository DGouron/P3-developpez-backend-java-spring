package com.oc.chatop.controllers;

import com.oc.chatop.dtos.RentalRequestDTO;
import com.oc.chatop.dtos.RentalResponseDTO;
import com.oc.chatop.entities.Rental;
import com.oc.chatop.services.RentalService;
import com.oc.chatop.utils.RentalMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Rentals", description = "Endpoints for rentals")
@RestController
@RequestMapping(value = "rentals")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    @Operation(summary = "Get rental by ID", description = "Fetches a specific rental by its ID along with the associated image name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the rental by ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rental = rentalService.findRentalById(Long.valueOf(id));
        return rental.map(r -> ResponseEntity.ok(rentalMapper.toRentalResponseDTO(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all rentals", description = "Fetches a list of all available rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all rentals",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<RentalResponseDTO>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        List<RentalResponseDTO> rentalResponseDTOs = rentals.stream()
                .map(rentalMapper::toRentalResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalResponseDTOs);
    }

    @Operation(
            summary = "Create a new rental",
            description = "Creates a new rental with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the rental"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RentalResponseDTO> createRental(@Parameter(
            description = "Parameters for create a new rental",
            required = true
    ) @Valid @ModelAttribute RentalRequestDTO rentalRequestDTO) {

        Rental rental = rentalMapper.toRental(rentalRequestDTO);
        Rental savedRental = rentalService.saveRental(rental);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalMapper.toRentalResponseDTO(savedRental));
    }

    @Operation(summary = "Update rental", description = "Updates an existing rental by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the rental"),
            @ApiResponse(responseCode = "404", description = "Rental not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
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