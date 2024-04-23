package com.proyectoDiseno.model;

import lombok.Data;

@Data
public class ServiceResponse {
    boolean success;
    String message;

    public void setMessage(String message) {
        this.message = message;

    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
