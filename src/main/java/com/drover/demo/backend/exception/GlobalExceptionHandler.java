package com.drover.demo.backend.exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiError> handleNotFound(
            RecursoNoEncontradoException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                404,
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errores.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        response.put("status", 400);
        response.put("errors", errores);
        response.put("path", request.getRequestURI());

        return ResponseEntity.badRequest().body(response);
    }   

    // 🟡 409 duplicado
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ApiError> handleDuplicado(
            RecursoDuplicadoException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                409,
                "CONFLICT",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(error);
    }

    // 🔵 negocio
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ApiError> handleNegocio(
            NegocioException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                400,
                "BUSINESS_ERROR",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    // 💣 error general
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleJsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                400,
                "BAD_REQUEST",
                "El formato de los datos enviados no es valido",
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<ApiError> handleValidacion(
            ValidacionException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                400,
                "VALIDATION_ERROR",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleParametroFaltante(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                400,
                "BAD_REQUEST",
                "Falta el campo obligatorio: " + ex.getParameterName(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiError> handleArchivoGrande(
            MaxUploadSizeExceededException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                400,
                "VALIDATION_ERROR",
                "La imagen supera el tamano maximo permitido",
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                500,
                "INTERNAL_SERVER_ERROR",
                "Error interno del servidor",
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(error);
    }
}
