package id.my.hendisantika.usecase;

import id.my.hendisantika.event.PaymentProducer;
import id.my.hendisantika.event.comsentation.SeatEventProducer;
import id.my.hendisantika.model.Payment;
import id.my.hendisantika.model.Seat;
import id.my.hendisantika.service.PaymentService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class MakePaymentUseCase {

    private final PaymentService paymentService;

    private final PaymentProducer paymentProducer;

    private final SeatEventProducer seatEventProducer;

    public Payment makeAPayment(Seat seat) {
        log.info("Create payment  with seat  {}", seat.getId());
        var payment = createPayment(seat);
        try {
            payment.setStatus("PAID");
            paymentService.savePayment(payment);
        } catch (Exception ex) {
            seatEventProducer.sendSeatEvent(payment.getSeat());
            return payment;
        }

        paymentProducer.sendPaymentEvent(payment);
        return payment;
    }

    private Payment createPayment(Seat seat) {
        Payment payment = new Payment();
        payment.setStatus("PAID");
        payment.setAmount(new BigDecimal(10));
        payment.setSeat(seat);
        payment.setUser(seat.getUser());
        payment.setDate(LocalDate.now());
        return payment;
    }
}
