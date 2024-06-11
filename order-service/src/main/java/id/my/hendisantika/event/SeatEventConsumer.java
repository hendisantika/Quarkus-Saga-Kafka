package id.my.hendisantika.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.model.Seat;
import id.my.hendisantika.service.SeatService;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 09:46
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class SeatEventConsumer {

    private final SeatService seatService;

    @SneakyThrows
    @Incoming("seats-in")
    public void receive(Record<Long, String> record) {
        log.info("record es: {}", record.key());
        ObjectMapper objectMapper = new ObjectMapper();

        var seat = objectMapper.readValue(record.value(), Seat.class);
        if (null != seat.getType() && seat.getType().equalsIgnoreCase("compensation")) {
            seatService.unlockSeat(seat.getId());
        } else {
            seatService.busySeat(seat.getId());
        }
    }
}
