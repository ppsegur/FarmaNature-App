package com.salesianostriana.dam.farma_app.upload.Post;
public record GetPostDto(
        Long id,
        String title,
        String imageUrl
) {

    public static GetPostDto of(Post post, String url) {
        return new GetPostDto(post.getId(), post.getTitle(), url);
    }

}