package com.ltp.ecommerce_books_store.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {


    private final String stripeSecretKey = "sk_test_51Pb3GrE63lbDO0hqarriuoajUjYd7DVE6WUpXmu9NVP08JP0CB2emjN0Ltrlnb7ujgCxGCgk9AhmO22LWpdwi7Fo00BDGXgzeQ";

    public PaymentServiceImpl() {
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public PaymentIntent createPaymentIntent(double amount, String currency, String userId, String bookId)
            throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (amount * 100)) 
                .setCurrency(currency) 
                .build();

        try {
            return PaymentIntent.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException("Payment failed: " + e.getMessage());
        }
    }

    @Override
    public PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            PaymentIntent updatedPaymentIntent = paymentIntent.confirm();

            if ("succeeded".equals(updatedPaymentIntent.getStatus())) {
                handleSuccessfulPayment(updatedPaymentIntent);
                return updatedPaymentIntent;
            } else {
                throw new RuntimeException("Payment failed: " + updatedPaymentIntent.getStatus());
            }
        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException("Payment confirmation failed: " + e.getMessage());
        }
    }

    private void handleSuccessfulPayment(PaymentIntent paymentIntent) {
        
    }
}
