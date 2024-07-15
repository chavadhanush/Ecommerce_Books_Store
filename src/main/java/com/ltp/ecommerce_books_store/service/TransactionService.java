package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Transaction;
import com.ltp.ecommerce_books_store.entity.User;

import java.util.List;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
    List<Transaction> findByUser(User user);
}
