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

    public List<JsonNode> getVehicles(String make) throws JsonProcessingException, VehiclesNotFoundException {
        List<Vehicle> vehicleList = new ArrayList<>();
        if(make!=null || !make.isEmpty())
            vehicleList  = vehicleRepository.findByMakeAllIgnoreCase(make);
       else
           vehicleList= (List<Vehicle>) vehicleRepository.findAll();

        if(vehicleList==null)
            throw new VehiclesNotFoundException("No Vehicles found");

        Vehicle vehicleObject = new Vehicle();
        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonNode> jsonNodeList = new ArrayList<>();
        for(Vehicle vehicle : vehicleList){
            vehicleObject.setId(vehicle.getId());
            System.out.println(vehicle.getMake());
            vehicleObject.setMake(vehicle.getMake());
            vehicleObject.setModel(vehicle.getModel());
            vehicleObject.setYear(vehicle.getYear());
            String stocksListJsonString = objectMapper.writeValueAsString(vehicleObject);
            JsonNode jsonNode = objectMapper.readTree(stocksListJsonString);
            jsonNodeList.add(jsonNode);
        }
        return jsonNodeList;
    }

    public JsonNode getVehicleById(int id) throws JsonProcessingException,VehicleNotFoundException{

        Optional<Vehicle> vehicleName = vehicleRepository.findById(id);

       if(!vehicleName.isPresent())
           throw new  VehicleNotFoundException("vehicle with id " + id+" not found");
        Vehicle vehicleObject = new Vehicle();
        vehicleObject.setId(vehicleName.get().getId());
        vehicleObject.setModel(vehicleName.get().getModel());
        vehicleObject.setYear(vehicleName.get().getYear());
        vehicleObject.setMake(vehicleName.get().getMake());

        ObjectMapper objectMapper = new ObjectMapper();
        String vehicleObjectJsonString = objectMapper.writeValueAsString(vehicleObject);
        JsonNode vehicleJsonObject = objectMapper.readTree(vehicleObjectJsonString);

        return vehicleJsonObject;
    }

    public Vehicle createVehicles(Vehicle vehicle) {

        Vehicle entity = vehicleRepository.save(vehicle);
        return entity;

    }



    public Vehicle updateVehicles(Vehicle vehicle) throws VehicleNotFoundException {
        Optional<Vehicle> vehicleById = vehicleRepository.findById(vehicle.getId());
        System.out.println("here after exception thrown");
        if(!vehicleById.isPresent()) {
            System.out.println("here in update vehicle not found exception");
            throw new VehicleNotFoundException("Vehicle with this " + vehicleById.get().getId() + " Not found");
        }
          System.out.println("here after exception thrown");
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
