package com.oc.chatop.controllers;

import com.oc.chatop.dtos.AuthRequestDTO;
import com.oc.chatop.dtos.AuthResponseDTO;
import com.oc.chatop.dtos.UserResponseDTO;
import com.oc.chatop.entities.User;
import com.oc.chatop.services.UserService;
import com.oc.chatop.utils.AuthMapper;
import com.oc.chatop.utils.JwtUtils;
import com.oc.chatop.utils.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(userMail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        // METTRE DANS UN SERVICE
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            String token = jwtUtils.generateToken(authRequest.getEmail());

            AuthResponseDTO authResponse = new AuthResponseDTO(token);
            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", authRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody AuthRequestDTO authRequestDTO) {
        User user = authMapper.toUser(authRequestDTO);
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // BOUGER CA DANS USER SERVICE DANS UNE FONCTION STYLE REGISTER USER OU AUTRE
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.saveUser(user);
        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }
}