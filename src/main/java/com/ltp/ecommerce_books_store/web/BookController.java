package com.ltp.ecommerce_books_store.web;

import com.ltp.ecommerce_books_store.entity.Book;
import com.ltp.ecommerce_books_store.entity.Evaluation;
import com.ltp.ecommerce_books_store.entity.Transaction;
import com.ltp.ecommerce_books_store.entity.User;
import com.ltp.ecommerce_books_store.exception.BookNotFoundException;
import com.ltp.ecommerce_books_store.payment.PaymentService;
import com.ltp.ecommerce_books_store.service.*;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final EvaluationService evaluationService;
    private final PaymentService paymentService;

    @Autowired
    public BookController(BookService bookService, UserService userService, TransactionService transactionService, EvaluationService evaluationService, PaymentService paymentService) {
        this.bookService = bookService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.evaluationService = evaluationService;
        this.paymentService = paymentService;
    }

    @GetMapping("/addBook")
    public String showBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @PostMapping("/books")
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-form";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/book/{id}")
    public String getBookById(@PathVariable Long id, Model model) {
        Book book = null;
        try {
            book = bookService.findById(id);
        } catch (Exception e) {
            throw new BookNotFoundException(id);
        }
        List<Evaluation> evaluations = evaluationService.findByBook(book);
        model.addAttribute("book", book);
        model.addAttribute("evaluations", evaluations);
        model.addAttribute("evaluation", new Evaluation());
        return "book-details";
    }

    @PostMapping("/book/{id}/review")
    public String submitReview(@PathVariable Long id, @ModelAttribute("evaluation") @Valid Evaluation evaluation, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Book book = bookService.findById(id);
            List<Evaluation> evaluations = evaluationService.findByBook(book);
            model.addAttribute("book", book);
            model.addAttribute("evaluations", evaluations);
            return "book-details";
        }

        Book book = bookService.findById(id);
        evaluation.setBook(book);
        evaluationService.saveEvaluation(evaluation);
        return "redirect:/book/" + id;
    }

    @PostMapping("/book/buy/{id}")
    public RedirectView buyBook(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Book book = bookService.findById(id);

        if (book.getQuantity() > 0) {
            bookService.buyBook(id, username);

            Transaction transaction = new Transaction();
            transaction.setUser(user);
            transaction.setBook(book);
            transaction.setTransactionType("BUY");
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setAmount(book.getPrice());
            transactionService.saveTransaction(transaction);

            try {
                String clientSecret = paymentService.createPaymentIntent(book.getPrice(), "usd", user.getId().toString(), book.getId().toString()).getClientSecret();
                return new RedirectView("/payment/complete?clientSecret=" + clientSecret);
            } catch (StripeException e) {
                e.printStackTrace();
            }
        }
        return new RedirectView("/book/" + id);
    }
}
