package com.mitchell_international.VehicleApplication.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring component which handles Exception and sends appropriate response from REST layer.
 * @author Akash Akki
 *
 */

@RestControllerAdvice
public class RestExceptionHandler  {


    /**
     *Method which handles {@link VehicleNotFoundException}. This methods returns error message
     * through {@link ErrorResponse} object which sends error code and the error message;
     * @param ex -{@link VehicleNotFoundException}
     * @return {@link ErrorResponse } containing error messages
     */
    @ExceptionHandler(value=VehicleNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionVehicleHandler(VehicleNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }


    /**
     * Method which handles {@link InputValidationException}. This methods returns error message
     * through {@link ErrorResponse} object which sends error code and the error message;
     *
     * @param inputValidationException -{@link InputValidationException}
     * @return {@link ErrorResponse} containing error messages
     */
    @ExceptionHandler(value = InputValidationException.class)
    public final ResponseEntity<ErrorResponse> hanldeValidationError(
            InputValidationException inputValidationException) {
        ErrorResponse error = new ErrorResponse();
        List<String> errorList = new ArrayList<>();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(inputValidationException.getErrors().getSpecificErrors().toString());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     *Method which handles {@link VehiclesNotFoundException}. This methods returns error message
     *through {@link ErrorResponse} object which sends error code and the error message;
     *
     * @param ex-{@link VehiclesNotFoundException}
     * @return {@link ErrorResponse} containing error messages
     */

    @ExceptionHandler(value=VehiclesNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionVehicleListHandler(VehiclesNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Method which handles generic {@link Exception}. This method respond with HTTP 500 status code
     * @param ex -  {@link Exception} object
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("The request could not be understood by the server due to malformed syntax.");
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
