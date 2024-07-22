package com.weinze.bank.account.rest.errors;

import java.io.Serializable;
import java.time.Instant;

public class ApiError implements Serializable {

    private int code;
    private String message;
    private Instant timestamp;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public ApiError(int code, String message, Instant timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
