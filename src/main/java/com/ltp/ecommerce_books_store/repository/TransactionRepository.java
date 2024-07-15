package com.ltp.ecommerce_books_store.repository;

import com.ltp.ecommerce_books_store.entity.Transaction;
import com.ltp.ecommerce_books_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}

