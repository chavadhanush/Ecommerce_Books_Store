package com.ltp.ecommerce_books_store.repository;

import com.ltp.ecommerce_books_store.entity.Book;
import com.ltp.ecommerce_books_store.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByBook(Book book);
}
