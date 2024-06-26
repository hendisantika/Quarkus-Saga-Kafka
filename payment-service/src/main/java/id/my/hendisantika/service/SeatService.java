package id.my.hendisantika.service;

import id.my.hendisantika.event.PaymentProducer;
import id.my.hendisantika.model.Seat;
import id.my.hendisantika.repository.SeatRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    @Inject
    private final SeatRepository seatRepository;

    @Inject
    private final PaymentProducer paymentProducer;

    @Transactional(REQUIRES_NEW)
    public Seat blockSeat(Seat seat) {
        log.info("Block a  seat ", seat.toString());

        seat.setStatus("BLOCKED");
        seatRepository.persistAndFlush(seat);
        return seat;
    }
}
