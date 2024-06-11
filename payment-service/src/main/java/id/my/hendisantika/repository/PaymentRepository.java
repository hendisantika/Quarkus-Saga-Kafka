package id.my.hendisantika.repository;

import id.my.hendisantika.model.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {
}
