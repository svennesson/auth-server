package se.svennesson.authserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BadCredentialsException extends WebApplicationException {

    public BadCredentialsException() {
        super("Wrong username and/or password. Please try again.", Response.Status.UNAUTHORIZED);
    }
}
