package com.ecommerce.imagen_service.service;

import com.ecommerce.imagen_service.dto.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ImageResponse upload(MultipartFile file);
}