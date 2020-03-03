package com.mitchell_international.VehicleApplication.Exception;


/**
 *
 * Custom Exception to catch when No vehicles are found
 * @author  Akash Akki
 *
 */
public class VehiclesNotFoundException extends Exception {

    public VehiclesNotFoundException(String message){

        super(message);
    }
}
