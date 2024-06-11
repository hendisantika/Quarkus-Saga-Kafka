package id.my.hendisantika.event;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;

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
}
