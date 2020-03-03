package com.mitchell_international.VehicleApplication.VehicleValidator;

import com.mitchell_international.VehicleApplication.Exception.InputValidationException;
import com.mitchell_international.VehicleApplication.Exception.ValidationErrors;
import com.mitchell_international.VehicleApplication.model.Vehicle;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator component to validate Vehicle attributes for different operations like create,
 * update.
 *
 * @author Akash Akki
 *
 */


@Component
public class VehicleValidator  {




    /**
     * Method to validate data in {@link Vehicle}, while updating it. Validation error details are
     * wrapped and sent across through {@link ValidationErrors} and {@link InputValidationException}
     *
     * @param  - {@link Vehicle} containing details which needs to be updated
     * @throws InputValidationException
     */
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


    /**
     *  Method to validate data in {@link Vehicle}, while creating it. Validation error details are
     *   wrapped and sent across through {@link ValidationErrors} and {@link InputValidationException}
     *
     * @param vehicle - {@link Vehicle} containing details which needs to be created
     * @throws InputValidationException
     */
        public void validateForCreate (Vehicle vehicle) throws InputValidationException {
              baseValidator(vehicle);
        }


    /**
     *     Base method used by {@link VehicleValidator#validateForCreate(Vehicle)} and
     *     {@link Vehicle# validateForUpdate(Vehicle)}. This contains common validations which needs
     *    to be done for both the method
     *     @param  - {@link Vehicle} containing details which needs to be updated
     *     @throws InputValidationException
     *
     * @param vehicle
    */
    private void baseValidator(Vehicle vehicle) throws InputValidationException {
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

