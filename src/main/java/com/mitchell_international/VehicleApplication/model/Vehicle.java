package com.mitchell_international.VehicleApplication.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name="Vehicle")
public class Vehicle implements Serializable {


    @Id
    private int Id;

    @Column(name="Year")
    @Range(min = 1951,max=2049)
    private  int Year;

    @Column(name="Make")
    @NotEmpty
    private String make;

    @Column(name="Model")
    @NotEmpty
    private String Model;

    public Vehicle(int id, int year, String make, String model) {
        this.Id =id;
        this.Year=year;
        this.make=make;
        this.Model=model;
    }

    public Vehicle() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }
}
