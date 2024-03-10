package com.example.katadelivery.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {

	public ClientNotFoundException() {
		super();
	}

    public ClientNotFoundException(String message) {
        super(message);
    }
}
