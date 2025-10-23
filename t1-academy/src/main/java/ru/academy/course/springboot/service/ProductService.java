package ru.academy.course.springboot.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.academy.course.springboot.entity.Product;
import ru.academy.course.springboot.model.ListProductResponseDto;
import ru.academy.course.springboot.model.ProductDto;
import ru.academy.course.springboot.model.ProductPaymentExecutionDto;
import ru.academy.course.springboot.repository.ProductRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDto getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(product -> new ProductDto(product.getAccountNumber(), product.getBalance(), product.getProductType()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public ListProductResponseDto getAllProducts() {
        return new ListProductResponseDto(productRepository.findAll()
                .stream()
                .map(p -> new ProductDto(p.getAccountNumber(), p.getBalance(), p.getProductType()))
                .toList());
    }

    @Transactional
    public ProductDto executePayment(String accountNumber, ProductPaymentExecutionDto request) {
        Product product = productRepository.findByAccountNumber(accountNumber)
                .orElseThrow(EntityNotFoundException::new);
        if (request.paymentAmount().compareTo(product.getBalance()) > 0) {
            throw new IllegalArgumentException("Недостаточно средств!");
        }
        product.setBalance(product.getBalance().subtract(request.paymentAmount()));
        productRepository.save(product);
        return new ProductDto(product.getAccountNumber(), product.getBalance(), product.getProductType());
    }
}
