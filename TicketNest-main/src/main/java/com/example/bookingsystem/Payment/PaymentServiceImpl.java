package com.example.bookingsystem.Payment;


import com.example.bookingsystem.Model.Order;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Value("${stripe.api.key}")
    private  String API_KEY;
    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {
        Stripe.apiKey=API_KEY;
        SessionCreateParams params=SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success?order_id="+order.getId())
                .setCancelUrl("http://localhost:8080/failed?order_id="+order.getId())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("INR")
                                .setUnitAmount((long)order.getTotalAmount()*100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("ticket")
                                        .build())
                                .build()
                        ).build()
                ).build();


        Session session=Session.create(params);

        PaymentResponse paymentResponse=new PaymentResponse();
        paymentResponse.setPayment_url(session.getUrl());


        return paymentResponse;
    }
}
