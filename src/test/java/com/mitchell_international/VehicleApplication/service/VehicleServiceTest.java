package com.mitchell_international.VehicleApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mitchell_international.VehicleApplication.Exception.VehicleNotFoundException;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.model.Vehicle;
import com.mitchell_international.VehicleApplication.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleServiceTest {

    @InjectMocks
    VehicleService vehicleService;

    @Mock
    VehicleRepository vehicleRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateVehicles(){
       Vehicle vehicle = new Vehicle();
       vehicle.setModel("SClass");
       vehicle.setYear(2018);
       vehicle.setId(1);
       vehicle.setMake("MercedesBenz");
       when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

       Vehicle vehicleEntity = vehicleService.createVehicles(vehicle);

       assertEquals(vehicle.getId(),vehicleEntity.getId());
       assertEquals(vehicle.getMake(),vehicleEntity.getMake());
       assertEquals(vehicle.getModel(),vehicleEntity.getModel());
       assertEquals(vehicle.getYear(),vehicle.getYear());
    }

    @Test
    public void shouldGetVehicleById() throws JsonProcessingException, VehicleNotFoundException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setMake("MercedesBenz");
        vehicle.setYear(2018);
        vehicle.setModel("GClass");

         when(vehicleRepository.findById(1)).thenReturn(java.util.Optional.of(vehicle));
         JsonNode vehicleObject = vehicleService.getVehicleById(1);
       System.out.println("year" +vehicleObject.get("year"));
       System.out.println("year vehicle "+ vehicle.getYear());


       assertEquals("1",vehicleObject.get("id").toString()) ;
       assertEquals("2018",vehicleObject.get("year").toString());
       assertEquals("GClass",vehicleObject.get("model").asText());
       assertEquals("MercedesBenz",vehicle.getMake().toString());
    }


    public void shouldGetVehiclesByIdWithResultNotPresent(){
        Vehicle vehicle = new Vehicle(1,2018,"MercedesBenz","GClass");
        when(vehicleRepository.findById(1)).thenReturn(Optional.empty());
           try{
               vehicleService.getVehicleById(1);
           } catch (VehicleNotFoundException e) {
               assertEquals("vehicle with id 1 does not exist",e.getMessage());
           }
           catch ( JsonProcessingException e){

           }
    }

//
//    @Test
//    public void shouldGetVehicles() throws JsonProcessingException, VehiclesNotFoundException {
//
//        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
//        Vehicle vehcile = new Vehicle(1, 1918, "MerecedesBenz", "SClass");
//        vehicleList.add(vehcile);
//        vehicleList.add(new Vehicle(2, 2020, "Audi", "Q6"));
//        vehicleList.add(new Vehicle(3, 2019, "Toyota", "Etios"));
//        when(vehicleRepository.findAll()).thenReturn(vehicleList);
//        List<JsonNode> vehicleJsonList = vehicleService.getVehicles(make);
//        assertEquals(vehicleList.size(), vehicleJsonList.size());
//    }

//    @Test
//    public void shouldGetVehiclesWithResultNotPresent(){
//        List<Vehicle> vehicleList = new ArrayList<>();
//        when(vehicleRepository.findAll()).thenReturn(vehicleList);
//        try{
//            List<JsonNode> vehicles = vehicleService.getVehicles(make);
//        }
//        catch (  VehiclesNotFoundException e){
//            assertEquals("No Vehicles found",e.getMessage());
//        }
//        catch (JsonProcessingException e){
//        }
//    }

    @Test
    public void shouldUpdateVehicles() throws VehicleNotFoundException {
        Vehicle vehicle=new Vehicle(1,2018,"MercedesBenz","GClass");
        when(vehicleRepository.findById(1)).thenReturn(java.util.Optional.of(vehicle));
        vehicle.setYear(2019);
        vehicle.setModel("EClass");
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Vehicle vehcileEntity = vehicleService.updateVehicles(vehicle);
        assertEquals(vehicle.getId(),vehcileEntity.getId());
        assertEquals(vehicle.getModel(),vehcileEntity.getModel());
    }

    @Test
    public void shouldUpdateVehiclesWithResultNotPresent() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        when(vehicleRepository.findById(1)).thenReturn(Optional.empty());
        try {
            vehicleService.updateVehicles(vehicle);
        } catch (VehicleNotFoundException e) {
            assertEquals("Vehicle Not found", e.getMessage());
        }
    }

    @Test
    public void shouldDeleteVehiclesById() throws VehicleNotFoundException {
//        Vehicle vehicle = new Vehicle();
//        vehicle.setId(1);
//        vehicle.setMake("MercedesBenz");
//        when(vehicleRepository.findById(1)).thenReturn(java.util.Optional.of(vehicle));
//        vehicleService.deleteVehiclesById(1);
//        verify(vehicleRepository, times(1)).deleteById(1);
    }

    @Test
    public void shouldDeleteVehiclesWithResultNotPresent(){
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setMake("MerecedesBenz");
        when(vehicleRepository.findById(1)).thenReturn(Optional.empty());
        try {
            vehicleService.deleteVehiclesById(vehicle.getId());
        }
        catch(VehicleNotFoundException e) {
             assertEquals("Vehicle with this id 1 not found",e.getMessage());
        }

    }
}
