package com.mitchell_international.VehicleApplication.controller;

import com.mitchell_international.VehicleApplication.Exception.InputValidationException;
import com.mitchell_international.VehicleApplication.Exception.VehicleNotFoundException;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.VehicleValidator.VehicleValidator;
import com.mitchell_international.VehicleApplication.model.Response;
import com.mitchell_international.VehicleApplication.model.Vehicle;
import com.mitchell_international.VehicleApplication.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/*    TODO
*register for machine learning exam
* 
*delete exception
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

@Api(value = "VehicleApplication", description = "Operations pertaining to Vehicle Application")

public class VehicleController {

   @Autowired
   VehicleService vehicleService;
   @Autowired
    VehicleValidator vehicleValidator;


    @GetMapping(value="/vehicles" , produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "View all the vehicles")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "json containing vehicles")})
    public ResponseEntity getVehicles(@RequestParam HashMap <String,String> requestParam ) throws  VehiclesNotFoundException {
        List<Vehicle> vehiclesList = new ArrayList<Vehicle>();
        vehiclesList = vehicleService.getVehicles(requestParam);
        System.out.println("herereeerer");
        if (vehiclesList==null || vehiclesList.size()==0)
               throw new VehiclesNotFoundException("No Vehicles found");
        return new ResponseEntity<List<Vehicle>>(vehiclesList,HttpStatus.OK);

    }

    @GetMapping(value="/vehicles/{Id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search a vehicle based on id (primary key)")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "produces a json "),
            @ApiResponse(code = 404, message = "Vehicle with specified id not found")})
    public  ResponseEntity getVehicleById(@PathVariable("Id") int Id) throws VehicleNotFoundException {
         Optional<Vehicle> vehicleById= vehicleService.getVehicleById(Id);

           System.out.println("vehcile"+vehicleById);
         return  new ResponseEntity<Optional<Vehicle>>(vehicleById,HttpStatus.OK);
    }

    @PostMapping(value = "/vehicles",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody

    @ApiOperation(value = "Add a Vehicle")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully retrieved todo"),
            @ApiResponse(code = 400, message = "Model is required,Make is required and " +
                    "Year should be between 1950 and 2050"),
            @ApiResponse(code = 500, message = "Internal Error")})
    public ResponseEntity createVehicle(@RequestBody Vehicle vehicle) throws InputValidationException {

        vehicleValidator.validateForCreate(vehicle);
        Vehicle vehicleObject = vehicleService.createVehicles(vehicle);
        return new ResponseEntity<Vehicle>(vehicleObject,HttpStatus.CREATED);
    }

    @PutMapping(value = "/vehicles" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = " Update a todo based on id (primary key)")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "json containing updated list of vehicle"),
            @ApiResponse(code = 400, message = "Model is required,Make is required and \" +\n" +
                    "                    \"Year should be between 1950 and 2050"),
            @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity updateVehicle(@RequestBody Vehicle vehicle) throws InputValidationException, VehicleNotFoundException {
        vehicleValidator.validForUpdate(vehicle);
        Vehicle vehicleObject = vehicleService.updateVehicles(vehicle);
        return new ResponseEntity<Vehicle>(vehicleObject,HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/vehicles/{Id}")
    @ApiOperation(value = "Delete a todo to based on id(primary key) and application id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Vehicle has been deleted"),
            @ApiResponse(code = 404, message = "The Vehicle with specfied id not found"),
            @ApiResponse(code = 500, message = "Internal Error")})
    public ResponseEntity<Response> deleteVehicleById(@PathVariable("Id") int Id) throws VehicleNotFoundException {
        vehicleService.deleteVehiclesById(Id);
        return new ResponseEntity<Response>(new Response(HttpStatus.NO_CONTENT.value(), "Vehicle has been deleted"),
                HttpStatus.NO_CONTENT);
    }


}
