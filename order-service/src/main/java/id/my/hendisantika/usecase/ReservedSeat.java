package id.my.hendisantika.usecase;

import id.my.hendisantika.event.SeatEventProducer;
import id.my.hendisantika.model.Seat;
import id.my.hendisantika.repository.UserRepository;
import id.my.hendisantika.service.SeatService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 09:44
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class ReservedSeat {

    @Inject
    private final SeatService seatService;

    private final UserRepository userRepository;
    @Inject
    private SeatEventProducer seatEventProducer;

    public Seat reservedSeat(Seat seat) {
        log.info("Update seat {}", seat.getId());
        var seatToSave = seatService.lockSeat(seat.getId());
        seatEventProducer.sendOrder(seatToSave);
        return seatToSave;
    }
}
