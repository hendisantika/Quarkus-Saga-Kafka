package id.my.hendisantika.controller;

import id.my.hendisantika.model.Seat;
import id.my.hendisantika.repository.SeatRepository;
import id.my.hendisantika.repository.UserRepository;
import id.my.hendisantika.service.SeatService;
import id.my.hendisantika.usecase.ReservedSeat;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @POST
    public Response orderSeat(Seat seat) {
        log.info("New order received ");
        return Response.status(Response.Status.CREATED)
                .entity(service.reservedSeat(seat)).build();
    }

    @GET
    @Path("/{id}")
    public Response orderSeat(@PathParam("id") Long id) {
        log.info("Seat status by id");
        return Response.status(Response.Status.OK)
                .entity(seatService.findById(id)).build();
    }
}
