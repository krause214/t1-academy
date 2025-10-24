package ru.academy.course.springboot.controller;

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
    public ListProductResponseDto getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getUserProducts(@PathVariable("id") Long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping("/{accountNumber}/pay")
    public ProductDto executePayment(@PathVariable("accountNumber") String accountNumber,
                                                      @RequestBody ProductPaymentExecutionDto request) {
        return productService.executePayment(accountNumber, request);
    }
}
