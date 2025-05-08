package com.example.payment_service.mapper;

import com.example.payment_service.dto.request.PaymentCreateRequest;
import com.example.payment_service.dto.response.PaymentResponse;
import com.example.payment_service.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPayment(PaymentCreateRequest paymentCreateRequest);
    PaymentResponse toPaymentResponse(Payment payment);
}
