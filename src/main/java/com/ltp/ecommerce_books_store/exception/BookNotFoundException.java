package com.ltp.ecommerce_books_store.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Book with id " + id + " not found");
    }
    public BookNotFoundException(String title) {
        super("Book with title '" + title + "' not found");
    }
}
