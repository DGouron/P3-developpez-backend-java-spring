package com.oc.chatop.controllers;

import com.oc.chatop.dtos.MessageRequestDTO;
import com.oc.chatop.dtos.MessageResponseDTO;
import com.oc.chatop.entities.Message;
import com.oc.chatop.services.MessageService;
import com.oc.chatop.utils.MessageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Messages", description = "Endpoints for messages to rental owners")
@RestController
@RequestMapping(value = "messages")
@RequiredArgsConstructor
public class MessagesController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @Operation(
            summary = "Post a message",
            description = "Send a message to a rental owner using his unique ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "The message was created with success"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @PostMapping
    public ResponseEntity<MessageResponseDTO> createMessage(@Parameter(
            description = "Parameters for create a new message",
            required = true
    )
            @Valid @RequestBody MessageRequestDTO messageRequestDTO) {

        Message message = messageMapper.toMessage(messageRequestDTO);
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageMapper.toMessageResponseDTO(savedMessage));
    }
}