package id.my.hendisantika.controller;

import id.my.hendisantika.repository.SeatRepository;
import id.my.hendisantika.repository.UserRepository;
import id.my.hendisantika.service.SeatService;
import id.my.hendisantika.usecase.ReservedSeat;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : Quarkus-Saga-Kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/11/24
 * Time: 09:49
 * To change this template use File | Settings | File Templates.
 */
@Path("/seats")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class OrderSeatResource {

    @Inject
    private final ReservedSeat service;

    @Inject
    private final UserRepository userRepository;

    @Inject
    private final SeatRepository seatRepository;

    private final SeatService seatService;
}
