package ca.bcit.infosys.comp4911.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Validation Helper
 */
public class ValidationHelper {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static Validator getValidator() {
        return factory.getValidator();
    }

    public static boolean validateEntity(Object object){
        Set<ConstraintViolation<Object>> constraintViolations = ValidationHelper.getValidator().validate(object);
        if (constraintViolations.size() > 0) {
            JSONArray errors = new JSONArray();

            for(ConstraintViolation<Object> constraintViolation:constraintViolations){
                JSONObject error = new JSONObject().
                  put("error",constraintViolation.getMessage());
                errors.put(error);
            }

            JSONObject jsonObject = new JSONObject().put("errors",errors);
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(jsonObject.toString()).build());
        }
        
        return true;
    }
}
