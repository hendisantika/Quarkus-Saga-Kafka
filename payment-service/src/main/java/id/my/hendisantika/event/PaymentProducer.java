package id.my.hendisantika.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.event.comsentation.SeatEventProducer;
import id.my.hendisantika.model.Payment;
import id.my.hendisantika.usecase.DeletePaymentUseCase;
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
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class PaymentProducer {

    private final SeatEventProducer seatEventProducer;
    private final DeletePaymentUseCase deletePaymentUseCase;
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
                        log.error("D'oh! {}", failure.getMessage());
                        seatEventProducer.sendSeatEvent(payment.getSeat());
                        deletePaymentUseCase.deletePayment(payment.getId());
                    } else {
                        log.info("Message processed successfully");
                    }
                });
    }
}
