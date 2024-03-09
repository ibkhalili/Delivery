package com.example.katadelivery.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientFoundException extends RuntimeException {

	public ClientFoundException() {
		super();
	}

    public ClientFoundException(String message) {
        super(message);
    }
}
