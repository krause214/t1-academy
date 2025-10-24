package ru.academy.pay.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.academy.pay.model.PaymentExecutionResponseDto;
import ru.academy.pay.model.PaymentRequestDto;
import ru.academy.pay.model.PaymentStage;
import ru.academy.pay.service.payment.executor.PaymentExecutor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CorePayService {

    private final Map<PaymentStage, PaymentExecutor> paymentExecutors;

    public CorePayService(List<PaymentExecutor> paymentExecutorList) {
        this.paymentExecutors = paymentExecutorList.stream().
                collect(Collectors.toMap(PaymentExecutor::getPaymentStage,
                paymentExecutor -> paymentExecutor));
    }

    public PaymentExecutionResponseDto handleRequest(PaymentRequestDto requestDto) {
        return paymentExecutors.get(requestDto.paymentStage()).executeStage(requestDto);
    }
}
