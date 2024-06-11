package id.my.hendisantika.event.compensation;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.model.Payment;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
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
 * Time: 09:13
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class PaymentProducerCompensation {

    @Channel("payments-out")
    Emitter<Record<Long, String>> emitter;

    @SneakyThrows
    public void sendPaymentEvent(Payment payment) {
        log.info("Event sent {}", payment.getId());

        ObjectMapper objectMapper = new ObjectMapper();
        var paymentJson = objectMapper.writeValueAsString(payment);

        emitter.send(Record.of(payment.getId(), paymentJson))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.info("Message unprocessed failure " + failure.getMessage());
                    } else {
                        log.info("Message processed successfully");
                    }
                });
    }
}
