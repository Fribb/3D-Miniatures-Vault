package net.fribbtastic.miniaturesvault.backend.v1.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Class to handle and manage a standardized response of the API
 *
 * @author Frederic Eßer
 */

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

    /**
     * the status code of the response
     */
    @JsonProperty("status")
    private int statusCode;

    /**
     * the object containing the actual data of the response
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * the Error object. Contains information about the error.
     */
    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorDetails errorDetails;

    /**
     * Create a Success Response with the Data object
     *
     * @param status the {@link HttpStatus} object
     * @param data   the data object
     * @param <T>    Generic type of the {@link ApiResponse}
     * @return an {@link ApiResponse} object with the data element set
     */
    public static <T> ApiResponse<T> createSuccessResponse(HttpStatus status, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(status.value());
        response.setData(data);

        return response;
    }

    /**
     * Create a Failure response with an error element
     *
     * @param status    the {@link HttpStatus} object
     * @param message   the short message of the error
     * @param exception the exception that was being thrown
     * @param <T>       Generic Type of the {@link ApiResponse}
     * @return an {@link ApiResponse} Object with the error element set
     */
    public static <T> ApiResponse<T> createFailureResponse(HttpStatus status, String message, Throwable exception) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(status.value());
        response.setErrorDetails(new ErrorDetails(message, exception.getMessage(), exception.getClass().getSimpleName()));

        return response;
    }
}
