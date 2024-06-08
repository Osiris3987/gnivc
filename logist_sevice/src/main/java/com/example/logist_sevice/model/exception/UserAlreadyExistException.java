package com.example.logist_sevice.model.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) { super(message); }
}
