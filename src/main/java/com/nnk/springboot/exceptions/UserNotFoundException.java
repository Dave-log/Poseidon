package com.nnk.springboot.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) { super(message); }
}
