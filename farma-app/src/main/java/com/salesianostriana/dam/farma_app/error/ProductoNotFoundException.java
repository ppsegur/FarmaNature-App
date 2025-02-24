package com.salesianostriana.dam.farma_app.error;

import jakarta.persistence.EntityNotFoundException;

public class ProductoNotFoundException extends EntityNotFoundException {

    public ProductoNotFoundException(String message){
        super("No se encuentran produtos");
    }
}
