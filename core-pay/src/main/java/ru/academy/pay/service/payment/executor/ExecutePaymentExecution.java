package ru.academy.pay.service.payment.executor;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import ru.academy.pay.client.ProductClient;
import ru.academy.pay.entity.PaymentExecution;
import ru.academy.pay.entity.PaymentStatus;
import ru.academy.pay.model.*;
import ru.academy.pay.repository.PaymentRepository;

@Component
public class ExecutePaymentExecution extends PaymentExecutor {

    private final ProductClient productClient;

    private final PaymentRepository paymentRepository;

    public ExecutePaymentExecution(ProductClient productClient, PaymentRepository paymentRepository) {
        this.productClient = productClient;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentExecutionResponseDto executeStage(PaymentRequestDto requestDto) {
        PaymentExecution paymentExecution = paymentRepository.findById(requestDto.executionId())
                .orElseThrow(EntityNotFoundException::new);

        checkPaymentStatus(paymentExecution);

        try {
            productClient.executePayment(requestDto.accountNumber(), requestDto.paymentAmount());
            ListProductResponseDto allProducts = productClient.getAllProducts();
            paymentExecution.setAccountNumber(requestDto.accountNumber());
            paymentExecution.setStatus(PaymentStatus.DONE);
            return new PaymentExecutionResponseDto(paymentExecution.getId(), allProducts);
        } catch (Exception e) {
            paymentExecution.setStatus(PaymentStatus.ERROR);
            throw e;
        } finally {
            paymentRepository.save(paymentExecution);
        }
    }

    @Override
    public PaymentStage getPaymentStage() {
        return PaymentStage.EXECUTE;
    }
}
