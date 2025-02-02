package com.example.PayoEat.response;

public class ApiResponse {
    private String message;
    private Object data;

    // No-argument constructor
    public ApiResponse() {
    }

    // Constructor with message and data
    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}