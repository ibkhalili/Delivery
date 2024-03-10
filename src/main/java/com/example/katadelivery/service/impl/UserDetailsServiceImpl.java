package com.example.katadelivery.service.impl;

import com.example.katadelivery.exceptions.ClientNotFoundException;
import com.example.katadelivery.models.Client;
import com.example.katadelivery.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) {

        Client user = repository.findByEmail(email).orElseThrow(() ->
                new ClientNotFoundException(String.format("User does not exist, email: %s", email)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
