package se.svennesson.izettle.exceptions;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class EntityNotFoundException extends WebApplicationException{

    public EntityNotFoundException() {
        super("Could not find entity", NOT_FOUND);
    }

    public EntityNotFoundException(Long id) {
        super("Could not find entity with id: " + id, NOT_FOUND);
    }
}
