package com.blck_rbbt.nordCodes.exceptions;

public class UrlValidationError {
    private String message;
    
    public UrlValidationError() {
    }
    
    public UrlValidationError(String message) {
        this.message = message;
    }
    
    public String getErrorMessage() {
        return message;
    }
    
    public void setErrorMessage(String message) {
        this.message = message;
    }
}
