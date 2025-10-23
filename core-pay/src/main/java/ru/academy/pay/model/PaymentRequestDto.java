package ru.academy.pay.model;

import java.math.BigDecimal;

public record PaymentRequestDto(Long executionId, PaymentStage paymentStage, String accountNumber, BigDecimal paymentAmount) {

}
