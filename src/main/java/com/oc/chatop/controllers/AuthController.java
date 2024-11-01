package com.oc.chatop.controllers;

import com.oc.chatop.dtos.UserLoginRequestDTO;
import com.oc.chatop.dtos.UserRegisterRequestDTO;
import com.oc.chatop.dtos.UserResponseDTO;
import com.oc.chatop.models.User;
import com.oc.chatop.services.UserService;
import com.oc.chatop.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;


@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("me")
    public ResponseEntity<UserResponseDTO> getMe() {
        Long userId = 2L;
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(UserMapper.toUserResponseDTO(user));
    }

    @PostMapping("register")
    public ResponseEntity<UserResponseDTO> postRegister(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        if (userService.findByEmail(userRegisterRequestDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User user = UserMapper.toUser(userRegisterRequestDTO);
        User savedUser = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponseDTO(savedUser));
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        Optional<User> user = userService.findByEmail(userLoginRequestDTO.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(userLoginRequestDTO.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
