package id.my.hendisantika.usecase;

import id.my.hendisantika.event.AllocateProducer;
import id.my.hendisantika.event.compensation.PaymentProducerCompensation;
import id.my.hendisantika.model.Seat;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 09:14
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class AllocateUseCase {

    private final SeatService seatService;

    private final PaymentProducerCompensation paymentProducerCompensation;

    private final AllocateProducer allocateProducer;


    public Seat updateSeat(Seat seat) {
        log.info("Save seat {}", seat.toString());

        try {
            seatService.updateSeat(seat);
            allocateProducer.sendSeatEvent(seat);
        } catch (Exception ex) {
            paymentProducerCompensation.sendPaymentEvent(seat.getPayment());
        }

        return seatService.findById(seat.getId());
    }
}
