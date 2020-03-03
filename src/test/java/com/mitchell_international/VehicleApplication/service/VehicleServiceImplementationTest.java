package com.mitchell_international.VehicleApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mitchell_international.VehicleApplication.Exception.VehicleNotFoundException;
import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.model.Vehicle;
import com.mitchell_international.VehicleApplication.repository.VehicleRepository;
import com.mitchell_international.VehicleApplication.service.Implementation.VehicleServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleServiceImplementationTest {

    @InjectMocks
    VehicleServiceImplementation vehicleServiceImplmentation;

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

       Vehicle vehicleEntity = vehicleServiceImplmentation.createVehicles(vehicle);

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

         Vehicle vehicleObject= vehicleServiceImplmentation.getVehicleById(1);


       assertEquals(1,vehicleObject.getId()) ;
       assertEquals(2018,vehicleObject.getYear());
       assertEquals("GClass",vehicleObject.getModel());
       assertEquals("MercedesBenz",vehicleObject.getMake());
    }


    @Test
    public void shouldGetVehiclesByIdWithResultNotPresent(){
        Vehicle vehicle = new Vehicle(1,2018,"MercedesBenz","GClass");
        when(vehicleRepository.findById(1)).thenReturn(Optional.empty());
           try{
               vehicleServiceImplmentation.getVehicleById(1);
           } catch (VehicleNotFoundException e) {
               assertEquals("vehicle with id 1 not found",e.getMessage());
           }

    }


    @Test
    public void shouldGetVehicles() throws JsonProcessingException, VehiclesNotFoundException {

        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        HashMap<String,String> requestParam =new HashMap<>();
        Vehicle vehcile = new Vehicle(1, 1918, "MerecedesBenz", "SClass");
        vehicleList.add(vehcile);
        vehicleList.add(new Vehicle(2, 2020, "Audi", "Q6"));
        vehicleList.add(new Vehicle(3, 2019, "Toyota", "Etios"));
        when(vehicleRepository.findAll()).thenReturn(vehicleList);
        List<Vehicle> vehicleObjectList = vehicleServiceImplmentation.getVehicles(requestParam);
        assertEquals(vehicleList.size(), vehicleObjectList.size());
    }

    @Test
    public void shouldGetVehiclesAllWithResultNotPresent() throws VehiclesNotFoundException {
        List<Vehicle> vehicleList =new ArrayList<Vehicle>();
        Vehicle vehcile = new Vehicle(1, 1918, "MerecedesBenz", "SClass");
        vehicleList.add(vehcile);
        vehicleList.add(new Vehicle(2, 2020, "Audi", "Q6"));
        vehicleList.add(new Vehicle(3, 2019, "Toyota", "Etios"));
        HashMap<String,String> requestParam = new HashMap<>();

        when(vehicleRepository.findAll()).thenReturn(vehicleList);
        List<Vehicle> vehicleObjectList = vehicleServiceImplmentation.getVehicles((requestParam));
        assertEquals(vehicleList.size(),vehicleObjectList.size());

    }

    @Test
    public void shouldGetVehiclesAllWithModelPresent() throws  VehiclesNotFoundException {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        Vehicle vehcile = new Vehicle(1, 1918, "MerecedesBenz", "SClass");
        vehicleList.add(vehcile);

        HashMap<String,String> requestParam = new HashMap<>();
        requestParam.put("model","SClass");
        when(vehicleRepository.findByModelAllIgnoreCase(requestParam.get("model"))).thenReturn(vehicleList);
        List<Vehicle> vehicleObjectList = vehicleServiceImplmentation.getVehicles(requestParam);
        assertEquals(vehicleList.size(),vehicleObjectList.size());
    }
    @Test
    public void shouldGetVehiclesAllWithMakePresent() throws  VehiclesNotFoundException {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        Vehicle vehcile = new Vehicle(1, 1918, "MerecedesBenz", "SClass");
        vehicleList.add(vehcile);

        HashMap<String,String> requestParam = new HashMap<>();
        requestParam.put("make","MerecedesBenz");

        when(vehicleRepository.findByMakeAllIgnoreCase(requestParam.get("make"))).thenReturn(vehicleList);

        List<Vehicle> vehicleObjectList = vehicleServiceImplmentation.getVehicles(requestParam);
        System.out.println("size is  "+vehicleObjectList.size());
        assertEquals(1,vehicleObjectList.size());

    }

    @Test
    public void shouldGetVehiclesWithResultNotPresent(){
        List<Vehicle> vehicleList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        when(vehicleRepository.findAll()).thenReturn(vehicleList);
        try{
            List<Vehicle> vehicles = vehicleServiceImplmentation.getVehicles(hashMap);
        }
        catch (  VehiclesNotFoundException e){
            assertEquals("No Vehicles found",e.getMessage());
        }
    }

    @Test
    public void shouldUpdateVehicles() throws VehicleNotFoundException {
        Vehicle vehicle=new Vehicle(1,2018,"MercedesBenz","GClass");
        when(vehicleRepository.findById(1)).thenReturn(java.util.Optional.of(vehicle));
        vehicle.setYear(2019);
        vehicle.setModel("EClass");
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Vehicle vehcileEntity = vehicleServiceImplmentation.updateVehicles(vehicle);
        assertEquals(vehicle.getId(),vehcileEntity.getId());
        assertEquals(vehicle.getModel(),vehcileEntity.getModel());
    }

    @Test
    public void shouldUpdateVehiclesWithResultNotPresent() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        when(vehicleRepository.findById(1)).thenReturn(Optional.empty());
        try {
            vehicleServiceImplmentation.updateVehicles(vehicle);
        } catch (VehicleNotFoundException e) {
            assertEquals("Vehicle with id 1 Not found", e.getMessage());
        }
    }

    @Test
    public void shouldDeleteVehiclesById() throws VehicleNotFoundException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setMake("MercedesBenz");
        when(vehicleRepository.findById(1)).thenReturn(java.util.Optional.of(vehicle));
        vehicleServiceImplmentation.deleteVehiclesById(1);
        verify(vehicleRepository, times(1)).deleteById(1);
    }

    @Test
    public void shouldDeleteVehiclesWithResultNotPresent(){
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setMake("MerecedesBenz");
        when(vehicleRepository.findById(1)).thenReturn(Optional.empty());
        try {
            vehicleServiceImplmentation.deleteVehiclesById(vehicle.getId());
        }
        catch(VehicleNotFoundException e) {
             assertEquals("Vehicle with this id 1 not found",e.getMessage());
        }

    }
}
