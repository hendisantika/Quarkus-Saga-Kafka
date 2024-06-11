package id.my.hendisantika.controller;

import id.my.hendisantika.service.SeatService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
 * Time: 09:18
 * To change this template use File | Settings | File Templates.
 */
@Path("/allocates")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class AllocateController {
    private final SeatService seatService;

    @GET
    @Path("/{id}")
    public Response allocateSeat(@PathParam("id") Long id) {

        log.info("Seat status by id");

        return Response.status(Response.Status.OK)
                .entity(seatService.findById(id)).build();
    }
}
