package se.svennesson.izettle.resources;

import com.codahale.metrics.annotation.Timed;
import se.svennesson.izettle.models.BasicCredentials;
import se.svennesson.izettle.models.User;
import se.svennesson.izettle.services.UserService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Context
    private UriInfo uriInfo;

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Timed
    @Path("register")
    public Response registerUser(@Valid User user) {
        return Response.created(uriInfo.getBaseUri()).entity(userService.registerUser(user)).build();
    }

    @POST
    @Timed
    @Path("login")
    public Response loginUser(@Valid BasicCredentials credentials) {
        return Response.ok(userService.loginUser(credentials)).build();
    }
}
