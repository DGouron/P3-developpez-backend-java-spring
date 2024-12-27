package com.oc.chatop.services;

import com.oc.chatop.providers.JwtProvider;
import com.oc.chatop.dtos.AuthRequestDTO;
import com.oc.chatop.entities.User;
import com.oc.chatop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtProvider JwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    // Méthode pour vérifier si l'utilisateur existe déjà
    public boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        var userToSave = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .build();
        return userRepository.save(userToSave);
    }

    public String authenticate(AuthRequestDTO authRequest) {
        try {
            // Chercher l'utilisateur par email
            User user = userRepository.findByEmail(authRequest.getEmail());
            if (user == null) {
                throw new BadCredentialsException("User not found");
            }

            // Vérification du mot de passe
            if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Incorrect password");
            }

            // Authentification réussie
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            // Récupérer les détails de l'utilisateur et générer un token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = JwtProvider.generateJwtToken(userDetails.getUsername());
            return token;

        } catch (BadCredentialsException e) {
            // Retourner null si les identifiants sont incorrects
            return null;
        }
    }
}