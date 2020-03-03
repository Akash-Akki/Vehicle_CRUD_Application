package com.mitchell_international.VehicleApplication.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 *    Object to transfer {@link Vehicle} entity details between Controller and Service layer and
 *    Object that used to persist in the database
 *
 */
@Entity
@Table(name="Vehicle")
public class Vehicle implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;

    @Column(name="Year")
    @Range(min = 1951,max=2049)
    private  int year;

    @Column(name="Make")
    @NotEmpty
    private String make;

    @Column(name="Model")
    @NotEmpty
    private String model;

    public Vehicle(int id, int year, String make, String model) {
        this.id =id;
        this.year =year;
        this.make=make;
        this.model =model;
    }

    public Vehicle() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
