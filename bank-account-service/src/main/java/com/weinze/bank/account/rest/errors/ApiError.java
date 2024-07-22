package com.weinze.bank.client.rest.errors;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;

public class ApiError implements Serializable {

    private HttpStatus code;
    private String message;
    private Instant timestamp;

    public ApiError(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public ApiError(HttpStatus code, String message, Instant timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
