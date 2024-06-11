package id.my.hendisantika.service;

import id.my.hendisantika.model.Seat;
import id.my.hendisantika.repository.SeatRepository;
import jakarta.enterprise.context.ApplicationScoped;
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
 * Time: 09:16
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional(REQUIRES_NEW)
    public void updateSeat(Seat seat) {
        log.info("Block a seat {}", seat.toString());
        seatRepository.update("status = 'OCCUPIED' where id = ?1", seat.getId());
    }

    public Seat findById(Long id) {
        return seatRepository.findById(id);
    }
}
