package com.mitchell_international.VehicleApplication.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.mitchell_international.VehicleApplication.Exception.VehiclesNotFoundException;
import com.mitchell_international.VehicleApplication.VehicleApplication;
import com.mitchell_international.VehicleApplication.model.Vehicle;
import com.mitchell_international.VehicleApplication.repository.VehicleRepository;
import com.mitchell_international.VehicleApplication.service.VehicleService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VehicleApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @InjectMocks
    VehicleController vehicleController;

    @Mock
    VehicleService vehicleService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void shouldGetAllVehicles() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3))).andDo(print());
    }

    @Test
    public void shouldGetVehilesWithEmptyList() throws  Exception{
         List<Vehicle> vehicleList = null;
         VehiclesNotFoundException vehiclesNotFoundException = new VehiclesNotFoundException("No Vehicle Found");
        HashMap<String,String> requestParam = new HashMap<>();

        when(vehicleService.getVehicles(requestParam)).thenThrow(vehiclesNotFoundException);
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles").accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.errorCode").exists())
//                .andExpect(jsonPath("$.message").exists())
//                .andExpect(jsonPath("$.errorCode").value("404"))
//                .andExpect(jsonPath("$.message").value("No Vehicles found"))
                .andDo(print());
    }

    @Test
    public void shouldGetVehicleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.make").exists())
                .andExpect(jsonPath("$.model").exists())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.year").value(2019))
                .andExpect(jsonPath("$.make").value("Audi"))
                .andExpect(jsonPath("$.model").value("Q7"))
                .andDo(print());
    }

   @Test
    public void shouldGetVehicleWithIdNotPresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("vehicle with id 0 not found"))
                .andDo(print());
    }
    @Test
    public void shouldCreateVehicles() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"4\", \"year\" : \"2019\",\"make\" : \"MercedesBenz\", \"model\" : \"GClass\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.make").exists())
                .andExpect(jsonPath("$.model").exists())
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.year").value("2019"))
                .andExpect(jsonPath("$.make").value("MercedesBenz"))
                .andExpect(jsonPath("$.model").value("GClass"))
                .andDo(print());

    }





    //    "errorCode": 400,
    //    "message": "{year=Year should be between 1950 and 2050, model=Model is required, make=Make is required}"




    @Test
    public void shouldCreateVehiclesWithAtrributesNotPresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"4\", \"year\" : \"2056\",\"make\" : \"\", \"model\" : \"\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorCode").value("400"))
                .andExpect(jsonPath("$.message").value("{year=Year should be between 1950 and 2050, model=Model is required, make=Make is required}"))
                .andDo(print());

    }

    //
    // "errorCode": 404,
    //    "message": "Vehicle with id 9 Not found"


    @Test
    public void shouldUpdateVehiclesWithIdNotPresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"99\", \"year\" : \"2020\",\"make\" : \"Audi\", \"model\" : \"Q7\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorCode").value("404"))
                .andExpect(jsonPath("$.message").value("Vehicle with id 99 Not found"))
                .andDo(print());

    }

    @Test
    public void shouldUpdateVehiclesWithAtrributesNotPresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"2\", \"year\" : \"2056\",\"make\" : \"\", \"model\" : \"\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorCode").value("400"))
                .andExpect(jsonPath("$.message").value("{model=Model is required, year=Year should be between 1950 and 2050, make=Make is required}"))
                .andDo(print());
    }


    @Test
    public void shouldUpdateVehicles() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"3\", \"year\" : \"2019\",\"make\" : \"MercedesBenz\", \"model\" : \"GClass\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.make").exists())
                .andExpect(jsonPath("$.model").exists())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.year").value("2019"))
                .andExpect(jsonPath("$.make").value("MercedesBenz"))
                .andExpect(jsonPath("$.model").value("GClass"))
                .andDo(print());
    }

    @Test
    public void shouldDeleteVehicle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles/4").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(204))
                .andExpect(jsonPath("$.message").value("Vehicle has been deleted"))
                .andDo(print());
    }

}





