package com.ecommerce.imagen_service.dto;

public record ImageResponse(
        String id,
        String url,
        String originalFileName,
        String contentType,
        Long size
) {
}