package com.example.katadelivery.controllers;

import com.example.katadelivery.dto.ClientDto;
import com.example.katadelivery.service.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    private final ClientService clientService;

    @SecurityRequirement(name = "deliveryApi")
    @GetMapping("/all")
    public ResponseEntity<List<ClientDto>> getAllClients() {
        log.info("Request to get all clients ...");
        return ResponseEntity.ok(clientService.getAllClients());
    }
}
