package com.mitchell_international.VehicleApplication.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchell_international.VehicleApplication.Exception.VehicleNotFoundException;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.mitchell_international.VehicleApplication.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {


    @Autowired
    VehicleRepository vehicleRepository;


    public List<JsonNode> getVehicles() throws JsonProcessingException {

        Iterable<Vehicle> all = vehicleRepository.findAll();
        Vehicle vehicleObject = new Vehicle();
        List<Vehicle> vehicleList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonNode> jsonNodeList = new ArrayList<>();

        for(Vehicle vehicle : all){
            vehicleObject.setId(vehicle.getId());
            vehicleObject.setMake(vehicle.getMake());
            vehicleObject.setModel(vehicle.getModel());
            vehicleObject.setYear(vehicle.getYear());
            String stocksListJsonString = objectMapper.writeValueAsString(vehicleObject);
            JsonNode jsonNode = objectMapper.readTree(stocksListJsonString);
            jsonNodeList.add(jsonNode);
        }

        if(jsonNodeList==null) {
            throw new VehiclesNotFoundException("No Vehicles Found");
        }

        return jsonNodeList;
    }

    public JsonNode getVehicleById(int id) throws JsonProcessingException {
        Optional<Vehicle> vehicleName = vehicleRepository.findById(id);
        Vehicle vehicleObject = new Vehicle();
        vehicleObject.setId(vehicleName.get().getId());
        vehicleObject.setModel(vehicleName.get().getModel());
        vehicleObject.setYear(vehicleName.get().getYear());
        vehicleObject.setMake(vehicleName.get().getMake());

        ObjectMapper objectMapper = new ObjectMapper();
        String vehicleObjectJsonString = objectMapper.writeValueAsString(vehicleObject);
        JsonNode vehicleJsonObject = objectMapper.readTree(vehicleObjectJsonString);


        if(vehicleJsonObject==null){
            throw new VehicleNotFoundException("This Vehicle is not found");
        }
        return vehicleJsonObject;


    }

    public void createVehicles(Vehicle vehicle) {
        Vehicle vehicleObject = new Vehicle();
        vehicleObject.setId(vehicle.getId());
        vehicleObject.setMake(vehicle.getMake());
        vehicleObject.setModel(vehicle.getModel());
        vehicleObject.setYear(vehicle.getYear());
        vehicleRepository.save(vehicleObject);
    }



    public void updateVehicles(Vehicle vehicle) {

        vehicleRepository.save(vehicle);
    }

    public void deleteVehiclesById(int id) {
        vehicleRepository.deleteById(id);
    }
}
