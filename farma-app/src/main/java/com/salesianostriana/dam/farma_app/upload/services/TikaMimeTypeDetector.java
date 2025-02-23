package com.salesianostriana.dam.farma_app.upload.services;

import io.jsonwebtoken.io.IOException;
import jakarta.annotation.Resource;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

@Component
public class TikaMimeTypeDetector implements MimeTypeDetector {

    private Tika tika;

    public TikaMimeTypeDetector() {
        tika = new Tika();
    }

    @Override
    public String getMimeType(Resource resource) {
        try {

            if (resource.isFile())
                return tika.detect(resource.getFile());
            else
                return tika.detect(resource.getInputStream());

        } catch (IOException ex) {
            throw new StorageException("Error trying to get the MIME type", ex);
        }
    }
}