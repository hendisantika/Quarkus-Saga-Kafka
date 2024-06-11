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
 * Time: 09:42
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional(REQUIRES_NEW)
    public Seat lockSeat(Long id) {
        log.info("Update seat with id {}", id);
        seatRepository.update("status = 'LOCKED' where id = ?1", id);
        return seatRepository.findById(id);
    }

    @Transactional(REQUIRES_NEW)
    public Seat unlockSeat(Long id) {
        log.info("Update seat with id {}", id);
        seatRepository.update("status = 'FREE' where id = ?1", id);
        return seatRepository.findById(id);
    }
}
