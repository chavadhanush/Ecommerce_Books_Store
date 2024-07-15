package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Transaction;
import com.ltp.ecommerce_books_store.entity.User;
import com.ltp.ecommerce_books_store.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public void saveTransaction(Transaction transaction) {
            transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findByUser(User user) {
        return transactionRepository.findByUser(user);
    }
}
