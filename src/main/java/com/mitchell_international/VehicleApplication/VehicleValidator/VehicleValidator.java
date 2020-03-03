package com.mitchell_international.VehicleApplication.VehicleValidator;

import com.mitchell_international.VehicleApplication.Exception.InputValidationException;
import com.mitchell_international.VehicleApplication.Exception.ValidationErrors;
import com.mitchell_international.VehicleApplication.model.Vehicle;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VehicleValidator  {

    public void validForUpdate(Vehicle vehicle) throws InputValidationException {
        ValidationErrors validationErrors = new ValidationErrors();
        if (StringUtils.isEmpty(vehicle.getId())) {
            validationErrors.addSpecificError("id", "id is required");
        }
        try {
            baseValidator(vehicle);
        } catch (InputValidationException inputValidationException) {
            if (inputValidationException.getErrors().isErrorAvailable()) {
                validationErrors.setErrorAvailable(true);
                validationErrors.getErrorMessages().addAll(inputValidationException.getErrors().getErrorMessages());
                validationErrors.getSpecificErrors().putAll(inputValidationException.getErrors().getSpecificErrors());
            }
        }
        if (validationErrors.isErrorAvailable()) {
            throw new InputValidationException(validationErrors);
        }
    }

        public void validateForCreate (Vehicle vehicle) throws InputValidationException {
             //System.out.println("here");
              baseValidator(vehicle);
        }



        private void baseValidator(Vehicle vehicle){
            ValidationErrors errors = new ValidationErrors();
            if (StringUtils.isEmpty(vehicle.getModel()))
                errors.addSpecificError("model", "Model is required");
            if (StringUtils.isEmpty(vehicle.getMake()))
                errors.addSpecificError("make", "Make is required");
            if(vehicle.getYear()>2050 || vehicle.getYear()<1950)
                 errors.addSpecificError("year","Year should be between 1950 and 2050");
            if (errors.isErrorAvailable()) {
                throw new InputValidationException(errors);
            }

        }


}

