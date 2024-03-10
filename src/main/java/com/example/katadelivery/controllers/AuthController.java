package com.example.katadelivery.controllers;

import com.example.katadelivery.dto.ClientDto;
import com.example.katadelivery.exceptions.UnauthorizedAuthException;
import com.example.katadelivery.security.jwt.JwtGenerate;
import com.example.katadelivery.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerate jwtGenerate;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody ClientDto client) {
        log.info("Adding new client [{}]...", client.getEmail());
        clientService.addClient(client);
        return ResponseEntity.ok("Client " + client.getEmail() + " est ajouté");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@Valid @RequestBody ClientDto client) {
        log.info("Authenticating client [{}]...", client.getEmail());
        String token;
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            client.getEmail(), client.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            ClientDto userProfile = clientService.getUserProfile(authentication.getName());
            token = jwtGenerate.generateToken(authentication, userProfile.getEmail());
            log.info(client.getEmail() + " Successefuly Authenticated\nAccess token: " + token);

        } catch (Exception e) {
            throw new UnauthorizedAuthException("Credentials are incorrect");
        }
        log.info("Client authenticated successfully");
        return ResponseEntity.ok(token);
    }
}
