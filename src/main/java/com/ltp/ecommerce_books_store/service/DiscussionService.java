package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Discussion;

import java.util.List;

public interface DiscussionService {
    void saveDiscussion(Discussion discussion);

    Discussion findById(Long id);

    List<Discussion> findAll();
}
