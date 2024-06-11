package id.my.hendisantika.usecase;

import id.my.hendisantika.event.comsentation.SeatEventProducer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
@ApplicationScoped
public class DeletePaymentUseCase {

    private final PaymentService paymentService;

    private final SeatEventProducer seatEventProducer;
}
