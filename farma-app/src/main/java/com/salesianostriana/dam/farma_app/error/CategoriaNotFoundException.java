package com.salesianostriana.dam.farma_app.error;

import jakarta.persistence.EntityNotFoundException;

public class CategoriaNotFoundException extends EntityNotFoundException {

    public CategoriaNotFoundException(String message){
        super("No se encuentran categorias");
    }
}
