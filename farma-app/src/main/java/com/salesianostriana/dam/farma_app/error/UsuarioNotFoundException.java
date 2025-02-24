package com.salesianostriana.dam.farma_app.error;

import jakarta.persistence.EntityNotFoundException;

public class UsuarioNotFoundException extends EntityNotFoundException {


    public UsuarioNotFoundException(String username){
        super("No se encontro ningun usuario por ese nombre "+username);
    }
}
