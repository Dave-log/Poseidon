package com.nnk.springboot.exceptions.notFound;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) { super(message); }
}
