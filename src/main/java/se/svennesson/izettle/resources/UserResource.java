package se.svennesson.izettle.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import se.svennesson.izettle.models.BasicCredentials;
import se.svennesson.izettle.models.User;
import se.svennesson.izettle.services.LoginAttemptsService;
import se.svennesson.izettle.services.UserService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Context
    private UriInfo uriInfo;

    private final UserService userService;
    private final LoginAttemptsService loginAttemptsService;

    public UserResource(UserService userService, LoginAttemptsService loginAttemptsService) {
        this.userService = userService;
        this.loginAttemptsService = loginAttemptsService;
    }

    @POST
    @Timed
    @Path("register")
    public Response registerUser(@Valid User user) {
        final User registeredUser = userService.registerUser(user);
        final UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        final URI userUri = uriBuilder
                .path("users/")
                .path(String.valueOf(registeredUser.getId()))
                .build();

        return Response.created(userUri).entity(registeredUser).build();
    }

    @POST
    @Timed
    @Path("login")
    public Response loginUser(@Valid BasicCredentials credentials) {
        return Response.ok(userService.loginUser(credentials)).build();
    }

    @GET
    @Timed
    @Path("attempts/success")
    public Response getLatestSuccessLoginAttempts(@Auth User user) {
        return Response.ok(loginAttemptsService.getLatestSuccessfulAttempts(user.getId())).build();
    }
}
