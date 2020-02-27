package com.mitchell_international.VehicleApplication.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.mitchell_international.VehicleApplication.VehicleApplication;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VehicleApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldGetAllVehicles() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
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

    @Test
    public void shouldUpdateVehicles() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"2\", \"year\" : \"2019\",\"make\" : \"MercedesBenz\", \"model\" : \"GClass\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.make").exists())
                .andExpect(jsonPath("$.model").exists())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.year").value("2019"))
                .andExpect(jsonPath("$.make").value("MercedesBenz"))
                .andExpect(jsonPath("$.model").value("GClass"))
                .andDo(print());
    }

    @Test
    public void shouldDeleteVehicle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(204))
                .andExpect(jsonPath("$.message").value("Vehicle has been deleted"))
                .andDo(print());
    }

}





