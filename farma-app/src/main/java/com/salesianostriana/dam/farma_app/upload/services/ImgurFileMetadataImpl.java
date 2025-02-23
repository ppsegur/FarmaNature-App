package com.salesianostriana.dam.farma_app.upload.services;


import com.salesianostriana.dam.farma_app.upload.AbstractFileMetadata;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ImgurFileMetadataImpl extends AbstractFileMetadata {

    public ImgurFileMetadataImpl(String id, String filename, String URL, String deleteId, String deleteURL) {
        super(id, filename, URL, deleteId, deleteURL);
    }
}
