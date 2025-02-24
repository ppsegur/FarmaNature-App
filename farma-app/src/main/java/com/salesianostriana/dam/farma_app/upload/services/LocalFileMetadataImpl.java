package com.salesianostriana.dam.farma_app.upload.services;


import com.salesianostriana.dam.farma_app.upload.AbstractFileMetadata;
import com.salesianostriana.dam.farma_app.upload.FileMetadata;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class LocalFileMetadataImpl extends AbstractFileMetadata {

    public static FileMetadata of(String filename) {
        return LocalFileMetadataImpl.builder()
                .id(filename)
                .filename(filename)
                .build();
    }

}