package com.salesianostriana.dam.farma_app.upload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AbstractFileMetadata implements FileMetadata{
    //protegidos
    protected String id;
    protected String filename;
    protected String URL;
    protected String deleteId;
    protected String deleteURL;
}
