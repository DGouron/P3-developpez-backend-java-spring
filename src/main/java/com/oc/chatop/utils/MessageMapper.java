package com.oc.chatop.utils;

import com.oc.chatop.dtos.MessageRequestDTO;
import com.oc.chatop.dtos.MessageResponseDTO;
import com.oc.chatop.entities.Message;
import com.oc.chatop.entities.Rental;
import com.oc.chatop.entities.User;
import com.oc.chatop.services.RentalService;
import com.oc.chatop.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    private final UserService userService;
    private final RentalService rentalService;

    public MessageMapper(UserService userService, RentalService rentalService) {
        this.userService = userService;
        this.rentalService = rentalService;
    }

    public Message toMessage(MessageRequestDTO messageRequestDTO) {
        Message message = new Message();
        message.setMessage(messageRequestDTO.getMessage());

        User user = userService.findUserById(messageRequestDTO.getUserId().longValue())
                .orElseThrow(() -> new RuntimeException("User not found"));
        message.setUser(user);

        Rental rental = rentalService.findRentalById(messageRequestDTO.getRentalId().longValue())
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        message.setRental(rental);

        return message;
    }

    public MessageResponseDTO toMessageResponseDTO(Message message) {
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        messageResponseDTO.setId(message.getId());
        messageResponseDTO.setMessage(message.getMessage());
        messageResponseDTO.setUserId(message.getUser().getId());
        messageResponseDTO.setRentalId(message.getRental().getId());
        return messageResponseDTO;
    }
}