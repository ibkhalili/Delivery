package com.example.katadelivery.service;

import com.example.katadelivery.dto.ClientDto;

import java.util.List;

public interface ClientService {

    void addClient(ClientDto client);

    List<ClientDto> getAllClients();

}
