package com.example.katadelivery.service.impl;

import com.example.katadelivery.dto.ClientDto;
import com.example.katadelivery.exceptions.BadRequestException;
import com.example.katadelivery.exceptions.ClientNotFoundException;
import com.example.katadelivery.models.Client;
import com.example.katadelivery.repository.ClientRepository;
import com.example.katadelivery.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addClient(ClientDto client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            log.error("email already exists");
            throw new BadRequestException("email already exists");
        }
        Client toSave = dtoToEntity(client);
        toSave.setPassword(passwordEncoder.encode(client.getPassword()));
        log.info("client toSave: {} ", toSave);
        clientRepository.save(toSave);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream().map(this::entityToDto).toList();
    }

    public ClientDto getUserProfile(String email) {
        return clientRepository.findByEmail(email).map(this::entityToDto)
                .orElseThrow(ClientNotFoundException::new);
    }


    private ClientDto entityToDto(Client entity) {
        return mapper.convertValue(entity, ClientDto.class);
    }

    private Client dtoToEntity(ClientDto dto) {
        return mapper.convertValue(dto, Client.class);
    }

}
