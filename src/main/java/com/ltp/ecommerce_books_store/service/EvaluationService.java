package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Book;
import com.ltp.ecommerce_books_store.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    void saveEvaluation(Evaluation evaluation);
    List<Evaluation> findByBook(Book book);
}
