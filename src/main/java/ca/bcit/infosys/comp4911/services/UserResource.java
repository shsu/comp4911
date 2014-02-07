package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.application.UserTokens;
import org.json.JSONObject;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@RequestScoped
@Path("/user")
public class UserResource implements Serializable {

    private static final String cors = "Access-Control-Allow-Origin";

    @Inject
    private UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAuthenticatedUserInfo() {

        return Response.ok().build();
    }

    @Path("/token")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(
      @HeaderParam("Authorization") final String authorization) {
        if (authorization != null && authorization.contains(":")) {
            String[] credentials = authorization.split(":");

            if (credentials[0].equals(credentials[1])) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", userTokens.generateToken(1));
                jsonObject.put("user_id", 1);

                return Response.ok().entity(jsonObject.toString()).
                  header(cors, "*").build();
            }
        }

        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }

    @Path("/token")
    @DELETE
    public Response invalidateToken(
      @HeaderParam("Authorization") final String authorization) {
        userTokens.clearToken(authorization);

        return Response.status(Response.Status.NO_CONTENT).header(cors, "*").build();
    }
}
