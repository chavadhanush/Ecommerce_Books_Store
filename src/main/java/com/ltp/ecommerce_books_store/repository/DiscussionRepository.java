package com.ltp.ecommerce_books_store.repository;

import com.ltp.ecommerce_books_store.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
}
