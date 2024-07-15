package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Book;
import com.ltp.ecommerce_books_store.entity.User;

import java.util.Set;

public interface UserService {
     User getUserById(Long id);

     User findByUsername(String username);

     User saveUser(User user);

     void deleteUserById(Long id);

     Set<Book> getUserBooks(String username);

     boolean existsByUsername(String username);

     Set<Book> getUserBooks(Long userId);
}
