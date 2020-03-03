package com.mitchell_international.VehicleApplication.repository;


import com.mitchell_international.VehicleApplication.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * CRUD Repository which contains built in method to create, update, retrieve and delete Vehicle
 * entity. Additional methods are also declared to fetch data on certain condition
 *
 * @author Akash Akki
 *
 * */

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Integer> {

    /**
     *  Method to fetch all Vehicles for given make attribute. Records will be matched by
     *  ignoring case for make attribute.
     * @param make  - Make attribute of the Vehicle Object
     * @return List of {@link Vehicle}
     */

    List<Vehicle> findByMakeAllIgnoreCase(String make);


    /**
     *  Method to fetch all Vehicles for given make attribute. Records will be matched by
     *  ignoring case for make attribute.
     * @param model -Make attribute of the Vehicle Object
     * @return  List of {@link Vehicle}
     */
    List<Vehicle> findByModelAllIgnoreCase(String model);
}
