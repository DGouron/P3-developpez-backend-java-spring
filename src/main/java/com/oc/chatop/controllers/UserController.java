package com.oc.chatop.controllers;

import com.oc.chatop.dtos.UserResponseDTO;
import com.oc.chatop.entities.User;
import com.oc.chatop.services.UserService;
import com.oc.chatop.utils.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "User", description = "Endpoints for user")
@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @Operation(summary = "Get user by ID", description = "Fetch and return user data using is unique ID")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
    Optional<User> user = userService.findUserById(id);
    return user.map(u -> ResponseEntity.ok(userMapper.toUserResponseDTO(u)))
            .orElseGet(() -> ResponseEntity.notFound().build());
  }
}