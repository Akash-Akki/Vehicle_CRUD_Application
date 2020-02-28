package com.mitchell_international.VehicleApplication.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.mitchell_international.VehicleApplication.Exception.VehicleNotFoundException;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.mitchell_international.VehicleApplication.model.Vehicle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;



    public List<Vehicle> getVehicles(HashMap<String, String> requestParam) throws  VehiclesNotFoundException {
        List<Vehicle> vehicleList = new ArrayList<>();
        if(requestParam.containsKey("make"))
            vehicleList  = vehicleRepository.findByMakeAllIgnoreCase(requestParam.get("make"));
       else if(requestParam.containsKey("model"))
           vehicleList = vehicleRepository.findByModelAllIgnoreCase(requestParam.get("model"));
       else
           vehicleList= (List<Vehicle>) vehicleRepository.findAll();

        if(vehicleList==null)
            throw new VehiclesNotFoundException("No Vehicles found");

        return vehicleList;
    }

    public Optional<Vehicle> getVehicleById(int id) throws VehicleNotFoundException{

        Optional<Vehicle> vehicleById = vehicleRepository.findById(id);

       if(!vehicleById.isPresent())
           throw new  VehicleNotFoundException("vehicle with id " + id+" not found");

        return vehicleById;
    }

    public Vehicle createVehicles(Vehicle vehicle) {
        Vehicle entity = vehicleRepository.save(vehicle);
        return entity;

    }



    public Vehicle updateVehicles(Vehicle vehicle) throws VehicleNotFoundException {

        Optional<Vehicle> vehicleById = vehicleRepository.findById(vehicle.getId());
        if(!vehicleById.isPresent()) {
            throw new VehicleNotFoundException("Vehicle with id " + vehicle.getId() + " Not found");
        }
          vehicleById.get().setMake(vehicle.getMake());
          vehicleById.get().setYear(vehicle.getYear());
          vehicleById.get().setModel(vehicle.getModel());
        Vehicle updatedEntity=vehicleRepository.save(vehicleById.get());
        return updatedEntity;

    }

    public void deleteVehiclesById(int id) throws VehicleNotFoundException {
        Optional<Vehicle> vehicleById = vehicleRepository.findById(id);
        if(vehicleById.isPresent())
            vehicleRepository.deleteById(id);
        else {
            throw new VehicleNotFoundException("Vehicle with this id " + id + " not found");
        }

    }
}
