package com.mitchell_international.VehicleApplication.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchell_international.VehicleApplication.Exception.*;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;

import com.mitchell_international.VehicleApplication.VehicleValidator.VehicleValidator;
import com.mitchell_international.VehicleApplication.model.Response;
import com.mitchell_international.VehicleApplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mitchell_international.VehicleApplication.model.Vehicle;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/*    TODO
*register for machine learning exam
*Cover Controller
* junits modification
* see delete why response is not coming when succesfully deleted
* and then see about validation errors and input validation exception.
*
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
   @Autowired
    VehicleValidator vehicleValidator;


    @GetMapping(value="/vehicles" , produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVehicles(@RequestParam HashMap <String,String> requestParam ) throws  VehiclesNotFoundException {
        List<Vehicle> vehiclesList = new ArrayList<Vehicle>();
        vehiclesList = vehicleService.getVehicles(requestParam);
            if (vehiclesList==null || vehiclesList.size()==0)
               throw new VehiclesNotFoundException("No Vehicles found");
        return new ResponseEntity<List<Vehicle>>(vehiclesList,HttpStatus.OK);

    }

    @GetMapping(value="/vehicles/{Id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity getVehicleById(@PathVariable("Id") int Id) throws VehicleNotFoundException {
         Optional<Vehicle> vehicleById= vehicleService.getVehicleById(Id);
           System.out.println("vehcile"+vehicleById);
          if(!vehicleById.isPresent()) {
              throw new VehicleNotFoundException();
          }
         return  new ResponseEntity<Optional<Vehicle>>(vehicleById,HttpStatus.OK);
    }

    @PostMapping(value = "/vehicles",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createVehicle(@RequestBody Vehicle vehicle) throws InputValidationException {

        vehicleValidator.validateForCreate(vehicle);
        Vehicle vehicleObject = vehicleService.createVehicles(vehicle);
        return new ResponseEntity<Vehicle>(vehicleObject,HttpStatus.CREATED);
    }

    @PutMapping(value = "/vehicles" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateVehicle(@Valid @RequestBody Vehicle vehicle) throws InputValidationException, VehicleNotFoundException {
        Vehicle vehicleObject = vehicleService.updateVehicles(vehicle);
        return new ResponseEntity<Vehicle>(vehicleObject,HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/vehicles/{Id}")
    public ResponseEntity<Response> deleteVehicleById(@PathVariable("Id") int Id) throws VehicleNotFoundException {
        vehicleService.deleteVehiclesById(Id);
        return new ResponseEntity<Response>(new Response(HttpStatus.NO_CONTENT.value(), "Vehicle has been deleted"),
                HttpStatus.NO_CONTENT);
    }


}
