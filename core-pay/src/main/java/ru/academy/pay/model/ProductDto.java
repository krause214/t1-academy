package ru.academy.pay.model;

import java.math.BigDecimal;

public record ProductDto(String accountNumber, BigDecimal balance, ProductType productType) {

}
