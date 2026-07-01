package com.ecommerce.imagen_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest request
    ) {
        log.warn(
                "Recurso no encontrado - Path: {}, Message: {}",
                request.getDescription(false),
                ex.getMessage()
        );

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        problemDetail.setTitle("Recurso no encontrado");
        problemDetail.setType(
                URI.create("https://api.ecommerce.com/errors/not-found")
        );
        problemDetail.setProperty("timestamp", Instant.now());

        problemDetail.setProperty("resource", ex.getResourceName());
        problemDetail.setProperty("field", ex.getFieldName());
        problemDetail.setProperty("value", ex.getFieldValue());

        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest request
    ) {
        log.warn(
                "Solicitud inválida - Path: {}, Message: {}",
                request.getDescription(false),
                ex.getMessage()
        );

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );

        problemDetail.setTitle("Solicitud inválida");
        problemDetail.setType(
                URI.create("https://api.ecommerce.com/errors/bad-request")
        );
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(
            Exception ex,
            WebRequest request
    ) {
        log.error(
                "Ocurrió un error inesperado - Path: {}, Message: {}",
                request.getDescription(false),
                ex.getMessage(),
                ex
        );

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error inesperado. Por favor, contacte al administrador."
        );

        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(
                URI.create("https://api.ecommerce.com/errors/internal")
        );
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}