package com.salesianostriana.dam.farma_app.error;

public class UsuarioNotFoundException extends RuntimeException{


    public UsuarioNotFoundException(String username){
        super("No se encontro ningun usuario por ese nombre "+username);
    }
}
