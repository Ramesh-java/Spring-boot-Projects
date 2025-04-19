package com.example.bookingsystem.Payment;

import com.example.bookingsystem.Model.Order;
import com.stripe.exception.StripeException;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}