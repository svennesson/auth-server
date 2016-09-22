package se.svennesson.izettle.resources;

import com.codahale.metrics.annotation.Timed;
import se.svennesson.izettle.models.User;
import se.svennesson.izettle.services.UserService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    private final UserService userService;

    public RegisterResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Timed
    public Response registerUser(@Valid User user) {
        return Response.ok(userService.registerUser(user)).build();
    }
}
