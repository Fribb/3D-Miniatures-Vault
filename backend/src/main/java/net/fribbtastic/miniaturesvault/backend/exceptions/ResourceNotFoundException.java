package net.fribbtastic.miniaturesvault.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * @author Frederic Eßer
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Exception to handle a Resource that wasn't found
     *
     * @param id the UUID of the resource
     */
    public ResourceNotFoundException(UUID id) {

        super(String.format("Resource with the id '%s' could not be found", id));
    }
}
