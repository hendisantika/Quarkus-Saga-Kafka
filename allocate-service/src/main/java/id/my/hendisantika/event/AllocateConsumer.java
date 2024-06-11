package id.my.hendisantika.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.model.Payment;
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
 * Time: 09:12
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class AllocateConsumer {

    private final AllocateUseCase allocateUseCase;

    @SneakyThrows
    @Incoming("allocates-in")
    public void receive(Record<Long, String> record) {
        log.info("Event received with key: {}", record.key());
        ObjectMapper objectMapper = new ObjectMapper();
        var payment = objectMapper.readValue(record.value(), Payment.class);
        var seat = payment.getSeat();
        allocateUseCase.updateSeat(seat);
    }
}
