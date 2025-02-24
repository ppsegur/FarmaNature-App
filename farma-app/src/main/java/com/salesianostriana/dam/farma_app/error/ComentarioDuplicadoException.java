package com.salesianostriana.dam.farma_app.error;

import org.springframework.http.HttpStatus;

public class ComentarioDuplicadoException extends EntidadNotFoundException {


    public ComentarioDuplicadoException(String message, HttpStatus status) {
        super(message, status);
    }
}