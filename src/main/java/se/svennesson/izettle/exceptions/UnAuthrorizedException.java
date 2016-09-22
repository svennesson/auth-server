package se.svennesson.izettle.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class UnAuthrorizedException extends WebApplicationException{

    public UnAuthrorizedException() {
        super("User is not authorized, please log in.", Response.Status.UNAUTHORIZED);
    }
}
