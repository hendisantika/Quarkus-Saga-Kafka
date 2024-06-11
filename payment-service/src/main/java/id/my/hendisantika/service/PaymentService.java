package id.my.hendisantika.service;

import id.my.hendisantika.event.comsentation.SeatEventProducer;
import id.my.hendisantika.model.Payment;
import id.my.hendisantika.repository.PaymentRepository;
import id.my.hendisantika.repository.SeatRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final SeatEventProducer seatEventProducer;

    private final SeatRepository seatRepository;

    @Transactional
    public Payment savePayment(Payment payment) {
        paymentRepository.persist(payment);
        return payment;
    }
}
