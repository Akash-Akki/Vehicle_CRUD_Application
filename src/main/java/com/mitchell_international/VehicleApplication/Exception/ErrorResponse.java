package com.mitchell_international.VehicleApplication.Exception;

/**
 *  DTO class to encapsulate error message which needs to be sent to user
 *
 * @author  Akash Akki
 */
public class ErrorResponse {

    private int errorCode;
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
