package com.salesianostriana.dam.farma_app.upload.services;



import com.salesianostriana.dam.farma_app.upload.FileMetadata;
import com.salesianostriana.dam.farma_app.upload.error.StorageException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Primary
@Service
public class FileSystemStorageService implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    private Path rootLocation;


    //carptea donde almacenaremos nuestros ficheros

    @PostConstruct
    @Override
    public void init() {
        rootLocation = Paths.get(storageLocation);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }

    }


    //Métdo para alamacear los ficheros y que devuelve los metadatos del fichero
    @Override
    public FileMetadata store(MultipartFile file)  {
        try {
            String filename =  store(file.getBytes(), file.getOriginalFilename(), file.getContentType());
            return LocalFileMetadataImpl.of(filename);
        } catch (Exception ex) {
            throw new StorageException("Error storing file: " + file.getOriginalFilename(), ex);
        }
    }
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            UrlResource resource =
                    new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + filename);
            }

        } catch (MalformedURLException ex) {
            throw new StorageException("Could not read file: " + filename);
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Files.delete(load(filename));
        } catch (IOException e) {
            throw new StorageException("Could not delete file:" + filename);
        }
    }

    private String store(byte[] file, String filename, String contentType) throws Exception {

        // Limpiamos el nombre del fichero
        String newFilename = StringUtils.cleanPath(filename);

        if (file.length == 0)
            throw new StorageException("The file is empty");

        newFilename = calculateNewFilename(newFilename);

        try (InputStream inputStream = new ByteArrayInputStream(file)) {
            Files.copy(inputStream, rootLocation.resolve(newFilename),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch(IOException ex) {
            throw new StorageException("Error storing file: " + newFilename, ex);
        }

        return newFilename;
    }
    //Creamos un nuevo nombre
    private String calculateNewFilename(String filename) {
        String newFilename = filename;

        while(Files.exists(rootLocation.resolve(newFilename))) {
            // Tratamos de generar un nuevo
            String extension = StringUtils.getFilenameExtension(newFilename);
            String name = newFilename.replace("." + extension, "");

            String suffix = Long.toString(System.currentTimeMillis());
            suffix = suffix.substring(suffix.length()-6);

            newFilename = name + "_" + suffix + "." + extension;

        }
        return newFilename;
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not delete all");
        }
    }
}