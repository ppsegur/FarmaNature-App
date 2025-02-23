package com.salesianostriana.dam.farma_app.error;

public class CategoriaNotFoundException extends RuntimeException{

    public CategoriaNotFoundException(String message){
        super("No se encuentran categorias");
    }
}
