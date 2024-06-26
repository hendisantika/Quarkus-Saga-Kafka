package id.my.hendisantika.service;

import id.my.hendisantika.event.compensation.PaymentProducerCompensation;
import id.my.hendisantika.model.Payment;
import id.my.hendisantika.repository.PaymentRepository;
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
 * Time: 09:15
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProducerCompensation paymentProducerCompensation;

    @Transactional
    public Payment savePayment(Payment payment) {
        try {
            paymentRepository.persistAndFlush(payment);
        } catch (Exception exception) {
            paymentProducerCompensation.sendPaymentEvent(payment);
        }
        return payment;
    }

    public void deletePayment(Long paymentId) {
        try {
            paymentRepository.deleteById(paymentId);
        } catch (Exception exception) {
            paymentProducerCompensation.sendPaymentEvent(paymentRepository.findById(paymentId));
        }
    }

}
