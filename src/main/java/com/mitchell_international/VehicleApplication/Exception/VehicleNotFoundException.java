package com.mitchell_international.VehicleApplication.Exception;

public class VehicleNotFoundException extends Exception {
    private  String errorMessage;

    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException() {
        super();
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
