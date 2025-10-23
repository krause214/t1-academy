package ru.academy.pay.service.payment.executor;

import ru.academy.pay.entity.PaymentExecution;
import ru.academy.pay.entity.PaymentStatus;
import ru.academy.pay.model.PaymentExecutionResponseDto;
import ru.academy.pay.model.PaymentRequestDto;
import ru.academy.pay.model.PaymentStage;

import java.util.List;

public abstract class PaymentExecutor {

    public void checkPaymentStatus(PaymentExecution paymentExecution) {
        if (List.of(PaymentStatus.DONE, PaymentStatus.ERROR).contains(paymentExecution.getStatus())) {
            throw new IllegalStateException(String.format("Заявка уже в финальном статусе - %s", paymentExecution.getStatus()));
        }
    }

    public abstract PaymentExecutionResponseDto executeStage(PaymentRequestDto requestDto);
    public abstract PaymentStage getPaymentStage();
}
