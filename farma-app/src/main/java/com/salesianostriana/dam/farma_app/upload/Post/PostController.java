package com.salesianostriana.dam.farma_app.upload.Post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<GetPostDto> create(
            @RequestParam("file") MultipartFile file,
            @RequestPart("post") CreatePostDto newPost
             ){
        Post post = service.save(newPost, file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(post.toString())
                .toUriString();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetPostDto.of(post,uri));
    }

}
