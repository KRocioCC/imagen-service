package com.ecommerce.imagen_service.service.Impl;

import com.ecommerce.imagen_service.dto.ImageResponse;
import com.ecommerce.imagen_service.exception.ResourceNotFoundException;
import com.ecommerce.imagen_service.model.ImageMetadata;
import com.ecommerce.imagen_service.repository.ImageMetadataRepository;
import com.ecommerce.imagen_service.service.ImageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.minio.GetObjectArgs;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import io.minio.RemoveObjectArgs;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final MinioClient minioClient;
    private final ImageMetadataRepository imageMetadataRepository;

    @Value("${app.minio.bucket}")
    private String bucket;

    @Override
    public ImageResponse upload(MultipartFile file) {
        validateFile(file);

        String imageId = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename());
        String objectKey = "products/" + imageId + "." + extension;

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "No se pudo guardar la imagen en MinIO",
                    exception
            );
        }

        ImageMetadata metadata = ImageMetadata.builder()
                .id(imageId)
                .objectKey(objectKey)
                .originalFileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .createdAt(Instant.now())
                .build();

        imageMetadataRepository.save(metadata);

        return new ImageResponse(
                metadata.getId(),
                "/api/images/" + metadata.getId(),
                metadata.getOriginalFileName(),
                metadata.getContentType(),
                metadata.getSize()
        );
    }

    @Override
    public ResponseEntity<InputStreamResource> getImage(String imageId) {
        ImageMetadata metadata = imageMetadataRepository.findById(imageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Imagen", "id", imageId)
                );

        try {
            InputStreamResource resource = new InputStreamResource(
                    minioClient.getObject(
                            GetObjectArgs.builder()
                                    .bucket(bucket)
                                    .object(metadata.getObjectKey())
                                    .build()
                    )
            );

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(metadata.getContentType()))
                    .contentLength(metadata.getSize())
                    .body(resource);

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "No se pudo obtener la imagen desde MinIO",
                    exception
            );
        }
    }
    //Metodo para eliminar la imagen del bucket y de la base de datos
    @Override
    public void deleteImage(String imageId) {
        ImageMetadata metadata = imageMetadataRepository.findById(imageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Imagen", "id", imageId)
                );

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(metadata.getObjectKey())
                            .build()
            );

            imageMetadataRepository.delete(metadata);

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "No se pudo eliminar la imagen de MinIO",
                    exception
            );
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Debes enviar una imagen.");
        }

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException(
                    "Formato no permitido. Usa JPG, PNG o WEBP."
            );
        }
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "bin";
        }

        return fileName.substring(fileName.lastIndexOf('.') + 1)
                .toLowerCase();
    }
}