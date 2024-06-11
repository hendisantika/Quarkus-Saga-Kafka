package id.my.hendisantika.event.comsentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.model.Seat;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 10:02
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class SeatEventProducer {

    @Inject
    @Channel("seats-out")
    Emitter<Record<Long, String>> emitter;

    @SneakyThrows
    public void sendSeatEvent(Seat seat) {
        log.info("Sent event to order service to compensate with seatId {}", seat.getId());
        seat.setType("compensation");
        ObjectMapper objectMapper = new ObjectMapper();
        var seatJson = objectMapper.writeValueAsString(seat);
        emitter.send(Record.of(seat.getId(), seatJson))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.error("failure sendo to message " + failure.getMessage());
                    } else {
                        log.info("Message processed successfully");
                    }
                });
    }
}
