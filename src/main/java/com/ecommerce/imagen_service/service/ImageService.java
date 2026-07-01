package com.ecommerce.imagen_service.service;

import com.ecommerce.imagen_service.dto.ImageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ImageResponse upload(MultipartFile file);

    ResponseEntity<InputStreamResource> getImage(String imageId);

    //Eliminar la imagen del bucket y de la base de datos
    void deleteImage(String imageId);

}