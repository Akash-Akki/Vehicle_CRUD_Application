package com.mitchell_international.VehicleApplication.service;

import com.mitchell_international.VehicleApplication.Exception.VehicleNotFoundException;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.model.Vehicle;
import java.util.HashMap;
import java.util.List;

/**
 * Service interface used by REST layer to working with Vehicle entity. Any implementation which will
 * work on Vehicle entity has to implement this interface. This interface exposes methods to create,
 * update, retrieve, delete and list Vehicles.
 *
 * @author Akash Akki
 *
 */
public interface VehicleService {



    /**
     *
     * @param requestParam request param taking attribute and value
     * @return List of Vehicles
     * @throws VehiclesNotFoundException
     */
    public List<Vehicle> getVehicles(HashMap<String,String> requestParam) throws VehiclesNotFoundException;


    /**
     * Method to retrieve Vehicle based on the primary key of Vehicle
     *
     * @param id - primary key of Vehicle
     *@return {@link Vehicle} which contains data persisted
     */
    public Vehicle getVehicleById(int id) throws VehicleNotFoundException;

    /**
     * Method to create Vehicle. Input details from external system is sent as {@link Vehicle}
     *
     * @param  - {@link Vehicle} which contains input details
     * @return {@link Vehicle} which contains primary key details of created Vehicle
     */
    public Vehicle createVehicles(Vehicle vehicle);

    /**
     *
     * Method to update Vehicle. Input details from external system is sent as {@link Vehicle}
     * @param  - {@link Vehicle} which contains input details
     * @return {@link Vehicle} which contains updated data that is persisted
     */
    public Vehicle updateVehicles(Vehicle vehicle) throws VehicleNotFoundException;



    /**
     *
     * Method to delete a Vehicle identified by primary key of Vehicle provided in id attribute
     * @param id - primary key of Vehicle.
     */
    public void deleteVehiclesById(int id) throws VehicleNotFoundException;

}
