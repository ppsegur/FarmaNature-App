package com.salesianostriana.dam.farma_app.error;

import org.springframework.http.HttpStatus;

public class VentaNotFoundException extends EntidadNotFoundException{

    public VentaNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
