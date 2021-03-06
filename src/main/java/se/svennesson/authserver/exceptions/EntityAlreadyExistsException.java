package se.svennesson.authserver.exceptions;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.CONFLICT;

public class EntityAlreadyExistsException extends WebApplicationException{

    public EntityAlreadyExistsException(String email) {
        super("User with email " + email + " already exists. Please log in instead.", CONFLICT);
    }
}
