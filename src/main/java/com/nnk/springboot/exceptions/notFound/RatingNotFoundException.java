package com.nnk.springboot.exceptions.notFound;

public class RatingNotFoundException extends RuntimeException {

    public RatingNotFoundException(String message) { super(message); }
}
