package com.example.katadelivery.controllers;

import com.example.katadelivery.dto.ClientDto;
import com.example.katadelivery.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody ClientDto client) {
        log.info("Adding new client [{}]...", client.getEmail());
        clientService.addClient(client);
        return ResponseEntity.ok("Client " + client.getEmail() + " est ajouté");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@Valid @RequestBody ClientDto client) {
        log.info("Authenticating client [{}]...", client.getEmail());
        clientService.addClient(client);
        return ResponseEntity.ok("Client " + client.getEmail() + " ....TOKEN....");
    }
}
