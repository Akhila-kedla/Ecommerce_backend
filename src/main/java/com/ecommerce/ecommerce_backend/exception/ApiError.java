package com.ecommerce.ecommerce_backend.exception;

import java.time.LocalDateTime;

public class ApiError {

    private LocalDateTime timestamp;
    private String message;
    private String path;

    public ApiError(String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
