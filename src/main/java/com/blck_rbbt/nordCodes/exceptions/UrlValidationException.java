package com.blck_rbbt.nordCodes.exceptions;


import java.util.List;

public class UrlValidationException extends RuntimeException {
    
    private String message;
    
    public UrlValidationException(String message) {
        super(message);
        this.message = message;
    }
    
    public String getErrorFieldsMessages() {
        return message;
    }
}
