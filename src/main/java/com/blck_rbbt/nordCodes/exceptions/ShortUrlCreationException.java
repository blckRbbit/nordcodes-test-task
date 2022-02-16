package com.blck_rbbt.nordCodes.exceptions;

public class ShortUrlCreationException  extends RuntimeException{
    private final String message;
    
    public ShortUrlCreationException(String message) {
        super(message);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
