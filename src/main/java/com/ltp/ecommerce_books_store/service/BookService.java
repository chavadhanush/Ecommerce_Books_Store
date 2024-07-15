package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    Book update(Book book);
    Book buyBook(Long bookId,String username);
    Book findByTitle(String title);
    void deleteBookById(Long id);
}
