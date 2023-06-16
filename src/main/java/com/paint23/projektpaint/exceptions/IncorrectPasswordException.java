package com.paint23.projektpaint.exceptions;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException(String errorMessage) {
        super(errorMessage);
    }
}