package ca.bcit.infosys.comp4911.services;

import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class TestResource {

    @GET
    @Produces("application/json")
    public Response test() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Test", "Successful");
        return Response.status(Response.Status.CREATED).entity(jsonObject.toString()).build();
    }

}
