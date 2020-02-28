package com.mitchell_international.VehicleApplication.repository;


import com.mitchell_international.VehicleApplication.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Integer> {

    List<Vehicle> findByMakeAllIgnoreCase(String make);
    List<Vehicle> findByModelAllIgnoreCase(String model);
}
