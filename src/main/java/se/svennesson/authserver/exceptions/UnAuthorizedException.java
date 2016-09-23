package se.svennesson.authserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class UnAuthorizedException extends WebApplicationException{

    public UnAuthorizedException() {
        super("User is not authorized, please log in.", Response.Status.UNAUTHORIZED);
    }
}
