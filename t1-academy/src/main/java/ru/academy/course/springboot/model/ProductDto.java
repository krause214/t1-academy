package ru.academy.course.springboot.model;

import ru.academy.course.springboot.entity.ProductType;

import java.math.BigDecimal;

public record ProductDto(String accountNumber, BigDecimal balance, ProductType productType) {}
