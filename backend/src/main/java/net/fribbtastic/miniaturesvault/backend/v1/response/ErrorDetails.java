package net.fribbtastic.miniaturesvault.backend.v1.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Frederic Eßer
 */
@Getter
public class ErrorDetails {

    /**
     * the Type of the exception
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String type;

    /**
     * a short description of the error
     */
    private final String message;

    /**
     * the detailed description of the error
     */
    private final String details;

    /**
     * the timestamp the error happened
     */
    private final String timestamp;

    /**
     * Construct an ErrorDetails Element by setting the message, a detailed error description and the type of the Exception.
     * This will add the current timestamp on creation.
     *
     * @param message a short message of the error
     * @param details a more detailed description about the error
     * @param type the type of an Exception.
     */
    public ErrorDetails(String message, String details, String type) {
        this.message = message;
        this.details = details;
        this.type = type;
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
