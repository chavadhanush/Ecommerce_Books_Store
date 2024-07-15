package com.ltp.ecommerce_books_store.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String user) {
        super("User " + user + " already exists");
    }
}
