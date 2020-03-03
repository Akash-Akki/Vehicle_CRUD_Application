/**
 * @author Akash Akki
 *
 */

package com.mitchell_international.VehicleApplication.controller;

import com.mitchell_international.VehicleApplication.Exception.InputValidationException;
import com.mitchell_international.VehicleApplication.Exception.VehicleNotFoundException;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.VehicleValidator.VehicleValidator;
import com.mitchell_international.VehicleApplication.model.Response;
import com.mitchell_international.VehicleApplication.model.Vehicle;
import com.mitchell_international.VehicleApplication.service.Implementation.VehicleServiceImplementation;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*    TODO
*  TodoService Interface
*
* 
*
*
* junits modification
* and then see about validation errors and input validation exception.
  Refactor code
  See about component creating two models
  update meaningful variable and method names
  add google styling
  validation
  Make datasamples to store it in tempororary files
  Add Java Docs


/**
 * Controller class which handles all the REST endpoint for Vehicle entity
 *
 * @author Akash Akki
 *
 */
@RestController
@Api(value = "VehicleApplication", description = "Operations pertaining to Vehicle Application")
public class VehicleController {

    /**
     * Spring data repository object, which helps to communicate to underlying database using JPA
     */
   @Autowired
    VehicleServiceImplementation vehicleServiceImplmentation;

    /**
     * Validator for input data
     */
   @Autowired
    VehicleValidator vehicleValidator;


    /**
     * Retrieves and Filters list of vehicle depend on the parameter supplied.
     * By Default it returns all the vehicles if parameter not supplied.
     * @param requestParam optional paramter Make Attribute or Model Attribute
     * @return  List Of Vehicles in the form of json.
     * @throws VehiclesNotFoundException
     */
    @GetMapping(value="/vehicles" , produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "View all the vehicles")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "json containing vehicles")})
    public ResponseEntity getVehicles(@ApiParam @RequestParam(required=false) HashMap <String,String> requestParam ) throws  VehiclesNotFoundException {
        List<Vehicle> vehiclesList = new ArrayList<Vehicle>();
        vehiclesList = vehicleServiceImplmentation.getVehicles(requestParam);
        if (vehiclesList==null || vehiclesList.size()==0)
               throw new VehiclesNotFoundException("No Vehicles found");
        return new ResponseEntity<List<Vehicle>>(vehiclesList,HttpStatus.OK);

    }


    /**
     *Retreives a vehicle based on primary key Id supplied.
     * @param Id  primary key value of Vehicle Object
     * @return Vehicle Object in the form of json
     * @throws VehicleNotFoundException
     */
    @GetMapping(value="/vehicles/{Id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search a vehicle based on id (primary key)")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "produces a json "),
            @ApiResponse(code = 404, message = "Vehicle with specified id not found")})
    public  ResponseEntity getVehicleById(@PathVariable("Id") int Id) throws VehicleNotFoundException {
         Vehicle vehicleById= vehicleServiceImplmentation.getVehicleById(Id);

           System.out.println("vehcile"+vehicleById);
         return  new ResponseEntity<Vehicle>(vehicleById,HttpStatus.OK);
    }


    /**
     * Creates a Vehicle in the database
     * @param vehicle Vehicle Object in the json format
     * @return created Vehicle Object
     * @throws InputValidationException
     */
    @PostMapping(value = "/vehicles",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "Add a Vehicle")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "json returning the created vehicle"),
            @ApiResponse(code = 400, message = "Model is required,Make is required and " +
                    "Year should be between 1950 and 2050"),
            @ApiResponse(code = 500, message = "Internal Error")})
    public ResponseEntity createVehicle(@RequestBody Vehicle vehicle) throws InputValidationException {

        vehicleValidator.validateForCreate(vehicle);
        System.out.println("in controller create before");
        Vehicle vehicleObject = vehicleServiceImplmentation.createVehicles(vehicle);
        System.out.println("in controller creare");
        return new ResponseEntity<Vehicle>(vehicleObject,HttpStatus.CREATED);
    }


    /**
     * Updates and returns a vehicle object with updated attributes
     * @param vehicle Vehicle Object
     * @return Updated Vehicle Object
     * @throws InputValidationException
     * @throws VehicleNotFoundException
     */
    @PutMapping(value = "/vehicles" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = " Update a Vehicle based on id (primary key)")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "json containing updated list of vehicle"),
            @ApiResponse(code = 400, message = "Model is required,Make is required and \" +\n" +
                    "                    \"Year should be between 1950 and 2050"),
            @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity updateVehicle(@RequestBody Vehicle vehicle) throws InputValidationException, VehicleNotFoundException {
        vehicleValidator.validForUpdate(vehicle);
        Vehicle vehicleObject = vehicleServiceImplmentation.updateVehicles(vehicle);
        return new ResponseEntity<Vehicle>(vehicleObject,HttpStatus.CREATED);
    }


    /**
     * Deletes a vehicle object based on the primary key supplied.
     * @param Id primary key of vehicle object
     * @return no content
     * @throws VehicleNotFoundException
     */
    @DeleteMapping(value = "/vehicles/{Id}")
    @ApiOperation(value = "Delete a Vehicle to based on id(primary key) and application id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Vehicle has been deleted"),
            @ApiResponse(code = 404, message = "The Vehicle with specfied id not found"),
            @ApiResponse(code = 500, message = "Internal Error")})
    public ResponseEntity<Response> deleteVehicleById(@PathVariable("Id") int Id) throws VehicleNotFoundException {
        vehicleServiceImplmentation.deleteVehiclesById(Id);
        return new ResponseEntity<Response>(new Response(HttpStatus.NO_CONTENT.value(), "Vehicle has been deleted"),
                HttpStatus.NO_CONTENT);
    }


}
