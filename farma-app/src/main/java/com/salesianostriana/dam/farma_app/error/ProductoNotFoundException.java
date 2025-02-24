package com.salesianostriana.dam.farma_app.error;

public class ProductoNotFoundException extends RuntimeException{

    public ProductoNotFoundException(String message){
        super("No se encuentran produtos");
    }
}
