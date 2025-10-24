package ru.academy.pay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.academy.pay.model.PaymentExecutionResponseDto;
import ru.academy.pay.model.PaymentRequestDto;
import ru.academy.pay.service.CorePayService;

@RestController
@RequestMapping("/api/core-pay")
public class CorePayController {

    private final CorePayService corePayService;

    public CorePayController(CorePayService corePayService) {
        this.corePayService = corePayService;
    }

    @PostMapping("/payment/execute")
    public PaymentExecutionResponseDto executePayment(@RequestBody PaymentRequestDto requestDto) {
        return corePayService.handleRequest(requestDto);
    }
}
