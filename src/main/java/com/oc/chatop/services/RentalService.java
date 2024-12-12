package com.oc.chatop.services;
import com.oc.chatop.entities.Rental;
import com.oc.chatop.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> getAllRentals() {
        return StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Optional<Rental> findRentalById(Long id) {
        return rentalRepository.findById(id);
    }
}
