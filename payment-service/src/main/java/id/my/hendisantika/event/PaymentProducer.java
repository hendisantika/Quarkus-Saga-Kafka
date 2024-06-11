package id.my.hendisantika.event;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;

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
}
