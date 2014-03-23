package ca.bcit.infosys.comp4911.helper;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Services Helper
 */
public class SH {
    public static final String AUTHORIZATION_STRING = "Authorization";
    public static final String TOKEN_STRING = "token";
    public static final String FILTER = "filter";
    public static final String YEAR = "year";
    public static final String WEEK = "week";

    public static javax.ws.rs.core.Response response(Integer code) {
        return javax.ws.rs.core.Response.status(code).build();
    }

    public static javax.ws.rs.core.Response responseWithEntity(int code, Object entity) {
        if (entity == null) {
            response(code);
        }

        return javax.ws.rs.core.Response.status(code).entity(entity).build();
    }

    public static void responseBadRequest(String errorMessage){
        if (errorMessage!= null) {
            JSONArray errors = new JSONArray();
            JSONObject error = new JSONObject().put("error",errorMessage);
            errors.put(error);
            JSONObject jsonObject = new JSONObject().put("errors",errors);
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(jsonObject.toString()).build());
        }
    }

    public static void responseBadRequest(Set<String> errorMessages){
        if (errorMessages!= null && errorMessages.size() > 0) {
            JSONArray errors = new JSONArray();

            for(String errorMessage:errorMessages){
                JSONObject error = new JSONObject().
                  put("error",errorMessage);
                errors.put(error);
            }

            JSONObject jsonObject = new JSONObject().put("errors",errors);
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(jsonObject.toString()).build());
        }
    }

    public static Integer getCurrentWeek() {
        return new DateTime().weekOfWeekyear().get();
    }

    public static Integer getCurrentYear() {
        return new DateTime().getYear();
    }
}
