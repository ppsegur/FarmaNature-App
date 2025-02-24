package com.salesianostriana.dam.farma_app.error;

import org.springframework.http.HttpStatus;

public class CategoriaNotFoundException extends EntidadNotFoundException {


    public CategoriaNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
