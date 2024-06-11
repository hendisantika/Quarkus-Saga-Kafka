package id.my.hendisantika.repository;

import id.my.hendisantika.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 09:11
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
