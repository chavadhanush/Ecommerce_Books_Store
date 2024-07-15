package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Discussion;
import com.ltp.ecommerce_books_store.repository.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository discussionRepository;

    @Autowired
    public DiscussionServiceImpl(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    @Override
    public void saveDiscussion(Discussion discussion) {
        discussionRepository.save(discussion);
    }

    @Override
    public Discussion findById(Long id) {
        return discussionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Discussion> findAll() {
        return discussionRepository.findAll();
    }
}
