package com.mitchell_international.VehicleApplication.Exception;

/**
 *  Custom Exception to Catch when vehicle with Specific id is not found.
 * @author  Akash Akki
 */
public class VehicleNotFoundException extends Exception {
    private  String errorMessage;

    public VehicleNotFoundException(String message) {
        super(message);
    }
}
