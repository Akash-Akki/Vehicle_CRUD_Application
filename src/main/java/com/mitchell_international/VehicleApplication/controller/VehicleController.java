package com.mitchell_international.VehicleApplication.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchell_international.VehicleApplication.Exception.*;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.model.Response;
import com.mitchell_international.VehicleApplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mitchell_international.VehicleApplication.model.Vehicle;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/*    TODO
*register for machine learning exam
*Cover Controller
* Add exception in create update nd delete
  Unit testing
  Add swagger documentation
  Refactor code
  See about component creating two models
  update meaningful variable and method names
  add google styling
  validation
  Make datasamples to store it in tempororary files
  Add Java Docs

  *
 */
@RestController
public class VehicleController {

   @Autowired
   VehicleService vehicleService;

    @GetMapping(value="/vehicles" , produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVehicles() throws JsonProcessingException, VehiclesNotFoundException {
        List<JsonNode> vehiclesList = new ArrayList<>();


            vehiclesList = vehicleService.getVehicles();
            if (vehiclesList==null || vehiclesList.size()==0)
               throw new VehiclesNotFoundException("No Vehicles found");

        return new ResponseEntity<List<JsonNode>>(vehiclesList,HttpStatus.OK);

    }

    @GetMapping(value="/vehicles/{Id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity getVehicleById(@PathVariable("Id") int Id) throws JsonProcessingException, VehicleNotFoundException ,Exception{
        JsonNode vehicleById;
        System.out.println(" here1");
           vehicleById= vehicleService.getVehicleById(Id);
           System.out.println("vehcile"+vehicleById);
//          if(vehicleById==null) {
//              throw new VehicleNotFoundException();
//          }
         return  new ResponseEntity<JsonNode>(vehicleById,HttpStatus.OK);
    }

    @PostMapping(value = "/vehicles",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createVehicle(@Valid @RequestBody Vehicle vehicle) throws JsonProcessingException {

        Vehicle vehicleObject = vehicleService.createVehicles(vehicle);
        ObjectMapper objectMapper = new ObjectMapper();
        String vehicleObjectJsonString = objectMapper.writeValueAsString(vehicleObject);
        JsonNode vehicleJsonObject = objectMapper.readTree(vehicleObjectJsonString);

        return new ResponseEntity<JsonNode>(vehicleJsonObject,HttpStatus.CREATED);
    }

    @PutMapping(value = "/vehicles" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateVehicle(@Valid @RequestBody Vehicle vehicle) throws JsonProcessingException, VehicleNotFoundException {

        Vehicle vehicleObject = vehicleService.updateVehicles(vehicle);
        if(vehicleObject==null)
             throw new VehicleNotFoundException();

        ObjectMapper objectMapper = new ObjectMapper();
        String vehicleObjectJsonString = objectMapper.writeValueAsString(vehicleObject);
        JsonNode vehicleJsonObject = objectMapper.readTree(vehicleObjectJsonString);

        return new ResponseEntity<JsonNode>(vehicleJsonObject,HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/vehicles/{Id}")
    public ResponseEntity<Response> deleteVehicleById(@PathVariable("Id") int Id) throws VehicleNotFoundException {

        vehicleService.deleteVehiclesById(Id);
        return new ResponseEntity<Response>(new Response(HttpStatus.NO_CONTENT.value(), "Vehicle has been deleted"),
                HttpStatus.NO_CONTENT);
    }


}
