package com.salesianostriana.dam.farma_app.upload.Post;

import com.salesianostriana.dam.farma_app.upload.FileMetadata;
import com.salesianostriana.dam.farma_app.upload.services.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final StorageService storageService;

    @Transactional
    public Post save(CreatePostDto createPostDto, MultipartFile file) {
        FileMetadata fileMetadata = storageService.store(file);

        Post post = repository.save(
                Post.builder()
                        .title(createPostDto.getTitle())
                        .image(fileMetadata.getFilename()).build()
        );
        return post;
    }

    public List<Post> findAll() {
        return repository.findAll();
    }
}
