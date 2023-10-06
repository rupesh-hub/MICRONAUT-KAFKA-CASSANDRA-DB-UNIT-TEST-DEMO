package com.rupesh.mdel;

import io.micronaut.serde.annotation.SerdeImport;

import java.time.Instant;

@SerdeImport(CustomResponse.class)
public class CustomResponse<T> {

    public CustomResponse() {
    }

    private String message;
    private String status;
    private Instant timestamp;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> CustomResponse<T> success(T data, String message) {
        CustomResponse<T> response = new CustomResponse<>();
        response.setMessage(message);
        response.setStatus("success");
        response.setTimestamp(Instant.now());
        response.setData(data);

        return response;
    }

    public static <T> CustomResponse<T> success(String message) {
        CustomResponse<T> response = new CustomResponse<>();
        response.setMessage(message);
        response.setStatus("error");
        response.setTimestamp(Instant.now());
        response.setData(null);

        return response;
    }
}
