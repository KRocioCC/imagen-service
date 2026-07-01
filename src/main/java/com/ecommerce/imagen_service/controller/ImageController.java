package com.ecommerce.imagen_service.controller;

import com.ecommerce.imagen_service.dto.ImageResponse;
import com.ecommerce.imagen_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponse> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {
        ImageResponse response = imageService.upload(file);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<InputStreamResource> getImage(
            @PathVariable String imageId
    ) {
        return imageService.getImage(imageId);
    }

    // Endpoint para eliminar una imagen por su ID
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable String imageId
    ) {
        imageService.deleteImage(imageId);

        return ResponseEntity.noContent().build();
    }
}