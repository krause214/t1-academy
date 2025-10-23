package ru.academy.course.springboot.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.academy.course.springboot.entity.User;
import ru.academy.course.springboot.model.ProductDto;
import ru.academy.course.springboot.model.ListProductResponseDto;
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
}
