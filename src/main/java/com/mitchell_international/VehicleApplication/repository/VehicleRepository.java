package com.mitchell_international.VehicleApplication.repository;


import com.mitchell_international.VehicleApplication.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Integer> {

}
