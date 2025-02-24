package com.salesianostriana.dam.farma_app.dto.user;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {
    public PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

}
