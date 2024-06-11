package id.my.hendisantika.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.model.Seat;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@Slf4j
public class SeatEventProducer {


    @Inject
    @Channel("seats-out")
    Emitter<Record<Long, String>> emitter;

    @SneakyThrows
    public void sendOrder(Seat seat) {
        log.info("Event sent {}", seat.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        var seatJson = objectMapper.writeValueAsString(seat);
        emitter.send(Record.of(seat.getId(), seatJson))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.error("failure process the message " + failure.getMessage());
                    } else {
                        log.info("Message processed successfully");
                    }
                });
    }
}
