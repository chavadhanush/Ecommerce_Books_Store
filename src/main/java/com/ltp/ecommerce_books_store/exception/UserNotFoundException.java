package com.ltp.ecommerce_books_store.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("The User with " + id + "is not found");
    }
    public UserNotFoundException(String username){
        super("The User with " + username + "is not found");
    }
}
