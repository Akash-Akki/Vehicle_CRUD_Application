package com.mitchell_international.VehicleApplication.Exception;

public class VehiclesNotFoundException extends Exception {

    public VehiclesNotFoundException(String message){

        super(message);
    }

    public VehiclesNotFoundException() {
        super();
    }
}
