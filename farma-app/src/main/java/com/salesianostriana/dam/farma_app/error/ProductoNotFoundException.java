package com.salesianostriana.dam.farma_app.error;

import org.springframework.http.HttpStatus;

public class ProductoNotFoundException extends EntidadNotFoundException {


    public ProductoNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
