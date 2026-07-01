package com.ecommerce.imagen_service.repository;

import com.ecommerce.imagen_service.model.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {
}