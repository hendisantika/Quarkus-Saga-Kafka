package id.my.hendisantika.event.comsentation;

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
 * Time: 10:01
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumerCompensation {

    private final DeletePaymentUseCase deletePaymentUseCase;

    private final SeatEventProducer seatEventProducer;

    @SneakyThrows
    @Incoming("allocate-in")
    public void receive(Record<Long, String> record) {
        log.info("Payment compensation with key {}", record.key());

        ObjectMapper objectMapper = new ObjectMapper();
        var payment = objectMapper.readValue(record.value(), Payment.class);

        deletePaymentUseCase.deletePayment(record.key());
        seatEventProducer.sendSeatEvent(payment.getSeat());
    }
}
