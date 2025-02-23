package com.salesianostriana.dam.farma_app.upload.services;

import jakarta.annotation.Resource;

public interface MimeTypeDetector {
    String getMimeType(Resource resource);

}
