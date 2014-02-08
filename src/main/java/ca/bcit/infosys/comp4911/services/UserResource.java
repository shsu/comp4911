package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.User;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

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

    @Inject
    private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAuthenticatedUserInfo(
      @HeaderParam("Authorization") final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        return Response.ok().entity(userDao.read(userId)).header(cors, "*").build();
    }

    @Path("/token")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(
      @HeaderParam("Authorization") final String authorization) {
        if (authorization != null && authorization.contains(":")) {
            String[] credentials = authorization.split(":");

            for (User user : userDao.getAll()) {
                if ((user.getUsername().equals(credentials[0]))
                  && BCrypt.checkpw(credentials[1], user.getPassword())) {

                    // Create a response with userId and token
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("user_id", user.getId());
                    jsonObject.put("token", userTokens.generateToken(user.getId().intValue()));
                    return Response.ok().entity(jsonObject.toString()).
                      header(cors, "*").build();
                }
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
