package com.salesianostriana.dam.farma_app.error;

import org.springframework.http.HttpStatus;

public class UsuarioNotFoundException extends EntidadNotFoundException {

    public UsuarioNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
