package com.salesianostriana.dam.farma_app.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntidadNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public EntidadNotFoundException(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }

}