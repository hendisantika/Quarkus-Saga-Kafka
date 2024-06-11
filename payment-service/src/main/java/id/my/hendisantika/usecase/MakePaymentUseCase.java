package id.my.hendisantika.usecase;

import id.my.hendisantika.event.PaymentProducer;
import id.my.hendisantika.event.comsentation.SeatEventProducer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class MakePaymentUseCase {

    private final PaymentService paymentService;

    private final PaymentProducer paymentProducer;

    private final SeatEventProducer seatEventProducer;

}
