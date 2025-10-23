package ru.academy.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.academy.pay.entity.PaymentExecution;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentExecution, Long> {

}
