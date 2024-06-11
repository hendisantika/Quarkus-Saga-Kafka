package id.my.hendisantika.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.model.Seat;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 09:13
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@Slf4j
public class AllocateProducer {

    @Channel("seats-out")
    Emitter<Record<Long, String>> emitter;

    @SneakyThrows
    public void sendSeatEvent(Seat seat) {
        log.info("Event sent seat with id{}", seat.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        var seatJson = objectMapper.writeValueAsString(seat);

        emitter.send(Record.of(seat.getId(), seatJson))
                .whenComplete((sucess, failure) -> {
                    if (failure != null) {
                        log.info("Message unprocessed failure " + failure.getMessage());
                    } else {
                        log.info("Message processed successfully");
                    }

                });
    }
}
