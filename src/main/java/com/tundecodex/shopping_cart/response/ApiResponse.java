package com.tundecodex.shopping_cart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data; // Allow flexibility for different response types

    // Constructor for errors without data
    public ApiResponse(String message, String error) {
        this.success = false;
        this.message = message;
        this.data = error;
    }

    // Constructor for success without data
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }
}
