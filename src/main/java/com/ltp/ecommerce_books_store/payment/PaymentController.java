package com.ltp.ecommerce_books_store.payment;

import com.ltp.ecommerce_books_store.service.BookService;
import com.ltp.ecommerce_books_store.service.TransactionService;
import com.ltp.ecommerce_books_store.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@SuppressWarnings("unused")
public class PaymentController {

    private final UserService userService;
    private final PaymentService paymentService;
    private final BookService bookService;
    private final TransactionService transactionService;

    public PaymentController(UserService userService, PaymentService paymentService, BookService bookService, TransactionService transactionService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.bookService = bookService;
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public String createPayment(@RequestParam double amount, @RequestParam String currency,
                                @RequestParam String userId, @RequestParam String bookId) {
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(amount, currency, userId, bookId);
            return paymentIntent.getClientSecret(); 
        } catch (StripeException e) {
            return "Error creating payment: " + e.getMessage();
        }
    }

    @GetMapping("/complete")
    public String completePayment(@RequestParam String clientSecret, Model model) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(clientSecret);
            PaymentIntent confirmedPaymentIntent = paymentService.confirmPaymentIntent(paymentIntent.getId());

            model.addAttribute("paymentIntent", confirmedPaymentIntent);

            Long amount = confirmedPaymentIntent.getAmount();
            Long amountCapturable = confirmedPaymentIntent.getAmountCapturable();
            PaymentIntent.AmountDetails amountDetails = confirmedPaymentIntent.getAmountDetails();

            model.addAttribute("amount", amount != null ? amount : 0);
            model.addAttribute("amountCapturable", amountCapturable != null ? amountCapturable : 0);
            model.addAttribute("amountDetails", amountDetails); 

            return "payment-success";
        } catch (StripeException e) {
            model.addAttribute("error", e.getMessage());
            return "payment-failure";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "payment-failure";
        }
    }

    @PostMapping("/confirm")
    public String confirmPayment(@RequestParam String paymentIntentId, Model model) {
        try {
            PaymentIntent confirmedPaymentIntent = paymentService.confirmPaymentIntent(paymentIntentId);

            model.addAttribute("paymentIntent", confirmedPaymentIntent);

            Long amount = confirmedPaymentIntent.getAmount();
            Long amountCapturable = confirmedPaymentIntent.getAmountCapturable();
            PaymentIntent.AmountDetails amountDetails = confirmedPaymentIntent.getAmountDetails();

            model.addAttribute("amount", amount != null ? amount : 0);
            model.addAttribute("amountCapturable", amountCapturable != null ? amountCapturable : 0);
            model.addAttribute("amountDetails", amountDetails); 

            return "payment-success";
        } catch (StripeException e) {
            model.addAttribute("error", e.getMessage());
            return "payment-failure";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "payment-failure";
        }
    }
}
