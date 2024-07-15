package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Book;
import com.ltp.ecommerce_books_store.entity.Evaluation;
import com.ltp.ecommerce_books_store.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService{

    private EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public List<Evaluation> findByBook(Book book) {
        return evaluationRepository.findByBook(book);
    }

    @Override
    public void saveEvaluation(Evaluation evaluation) {
        evaluationRepository.save(evaluation);
    }
}
