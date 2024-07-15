package com.ltp.ecommerce_books_store.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface PaymentService {
    PaymentIntent createPaymentIntent(double amount, String currency, String userId, String bookId) throws StripeException;
    PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException;
}
