package com.blck_rbbt.nordCodes.exceptions;

public class ShortUrlCreationError {
    private String message;
    
    public ShortUrlCreationError() {
    }
    
    public ShortUrlCreationError(String message) {
        this.message = message;
    }
    
    public String getErrorMessage() {
        return message;
    }
    
    public void setErrorMessage(String message) {
        this.message = message;
    }
}
