package com.salesianostriana.dam.farma_app.upload.services;


import com.salesianostriana.dam.farma_app.upload.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    FileMetadata store(MultipartFile file);

    Resource loadAsResource(String filename);

    void deleteFile(String filename);
}