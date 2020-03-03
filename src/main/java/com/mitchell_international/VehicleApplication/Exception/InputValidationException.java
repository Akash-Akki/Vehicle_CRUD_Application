package com.mitchell_international.VehicleApplication.Exception;

/**
 * This exception wraps error messages using {@link ValidationErrors} object and can be propagated
 * to REST layer. As name suggest this method should be used to wrap and send any input validation
 *  error messages. Please refer {@link RestExceptionHandler} for HTTP response code details
 * sent for this exception class
 *
 * @author Akash Akki
 *
 */
public class InputValidationException extends RuntimeException {


    /**
     * Object to hold validation error message
     */
    private ValidationErrors errors;

    /** Constructor */
    public InputValidationException(ValidationErrors errors) {
        this.errors = errors;
    }

    public ValidationErrors getErrors() {
        return errors;
    }


}