package com.ecommerce.imagen_service.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(value = "image_metadata")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageMetadata {

    @Id
    private String id;

    private String objectKey;
    private String originalFileName;
    private String contentType;
    private Long size;

    private Instant createdAt;
}