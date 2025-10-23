package ru.academy.course.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.academy.course.springboot.model.ListProductResponseDto;
import ru.academy.course.springboot.model.ProductDto;
import ru.academy.course.springboot.model.ProductPaymentExecutionDto;
import ru.academy.course.springboot.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<ListProductResponseDto> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getUserProducts(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PostMapping("/{accountNumber}/pay")
    public ResponseEntity<ProductDto> executePayment(@PathVariable("accountNumber") String accountNumber,
                                                      @RequestBody ProductPaymentExecutionDto request) {
        return ResponseEntity.ok(productService.executePayment(accountNumber, request));
    }
}
