package com.ltp.ecommerce_books_store.service;

import com.ltp.ecommerce_books_store.entity.Book;
import com.ltp.ecommerce_books_store.entity.User;
import com.ltp.ecommerce_books_store.exception.BookNotFoundException;
import com.ltp.ecommerce_books_store.repository.BookRepository;
import com.ltp.ecommerce_books_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return unwrapBook(book, id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setDescription(book.getDescription());
            return bookRepository.save(existingBook);
        } else {
            throw new BookNotFoundException(book.getId());
        }
    }

    @Override
    @Transactional
    public Book buyBook(Long bookId, String username) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalBook.isPresent() && optionalUser.isPresent()) {
            Book existingBook = optionalBook.get();
            User existingUser = optionalUser.get();

            if (existingBook.getQuantity() > 0) {
                existingBook.setQuantity(existingBook.getQuantity() - 1);
                existingBook.getUsers().add(existingUser);
                existingUser.getBooks().add(existingBook);

                bookRepository.save(existingBook);
                userRepository.save(existingUser);

                return existingBook;
            } else {
                throw new RuntimeException("Book is out of stock");
            }
        } else {
            throw new BookNotFoundException(bookId);
        }
    }

    @Override
    public Book findByTitle(String title) {
        Optional<Book> existingBook = bookRepository.findBookByTitle(title);
        return unwrapBook(existingBook, title);
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    static Book unwrapBook(Optional<Book> entity, Long id) {
        return entity.orElseThrow(() -> new BookNotFoundException(id));
    }

    static Book unwrapBook(Optional<Book> entity, String title) {
        return entity.orElseThrow(() -> new BookNotFoundException(title));
    }
}
