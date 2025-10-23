package ru.academy.pay.model;

import java.math.BigDecimal;

public record ProductPaymentExecutionDto(BigDecimal paymentAmount) {
}
