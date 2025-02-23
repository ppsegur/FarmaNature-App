package com.salesianostriana.dam.farma_app.upload.error;


public class StorageException extends RuntimeException{

    public StorageException(String msg) {
        super(msg);
    }

    public StorageException(String msg, Exception e) {
        super(msg, e);
    }

}