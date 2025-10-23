package ru.academy.pay.service.payment.executor;

import org.springframework.stereotype.Component;
import ru.academy.pay.client.ProductClient;
import ru.academy.pay.entity.PaymentExecution;
import ru.academy.pay.entity.PaymentStatus;
import ru.academy.pay.model.ListProductResponseDto;
import ru.academy.pay.model.PaymentExecutionResponseDto;
import ru.academy.pay.model.PaymentRequestDto;
import ru.academy.pay.model.PaymentStage;
import ru.academy.pay.repository.PaymentRepository;

@Component
public class InitPaymentExecution extends PaymentExecutor {

    private final ProductClient productClient;

    private final PaymentRepository paymentRepository;

    public InitPaymentExecution(ProductClient productClient, PaymentRepository paymentRepository) {
        this.productClient = productClient;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentExecutionResponseDto executeStage(PaymentRequestDto requestDto) {
        PaymentExecution paymentExecution = paymentRepository.save(new PaymentExecution());
        try {
            ListProductResponseDto allProducts = productClient.getAllProducts();
            paymentExecution.setStatus(PaymentStatus.INITIALIZED);
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
        return PaymentStage.INIT;
    }
}
