package com.mitchell_international.VehicleApplication.Exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class RestExceptionHandler  {

    @ExceptionHandler(value=VehicleNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionVehicleHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InputValidationException.class)
    public final ResponseEntity<ErrorResponse> hanldeValidationError(
            InputValidationException inputValidationException) {
        ErrorResponse error = new ErrorResponse();
        List<String> errorList = new ArrayList<>();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(inputValidationException.getErrors().getSpecificErrors().toString());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value=VehiclesNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionVehicleListHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("The request could not be understood by the server due to malformed syntax.");
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }




}
