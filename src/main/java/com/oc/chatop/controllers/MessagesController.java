package com.oc.chatop.controllers;

import com.oc.chatop.dtos.MessageRequestDTO;
import com.oc.chatop.dtos.MessageResponseDTO;
import com.oc.chatop.entities.Message;
import com.oc.chatop.services.MessageService;
import com.oc.chatop.utils.MessageMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "messages")
@RequiredArgsConstructor
public class MessagesController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createMessage(
            @Valid @RequestBody MessageRequestDTO messageRequestDTO) {

        Message message = messageMapper.toMessage(messageRequestDTO);
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageMapper.toMessageResponseDTO(savedMessage));
    }
}