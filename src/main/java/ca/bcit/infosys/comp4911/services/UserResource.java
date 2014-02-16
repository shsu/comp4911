package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserResource {

    @EJB
    private UserTokens userTokens;

    @EJB
    private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAuthenticatedUserInfo(
      @HeaderParam(SH.auth) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        return Response.ok().entity(userDao.read(userId)).header(SH.cors, "*").build();
    }

    @Path("/token")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(
      @HeaderParam(SH.auth) final String token) {
        if (Strings.isNullOrEmpty(token)) {
            throw new WebApplicationException(
              Response.status(Response.Status.UNAUTHORIZED).header(SH.cors, "*").build());
        }

        String decodedCredentials = new String(
          BaseEncoding.base64().decode(token.substring("Basic ".length())), Charsets.UTF_8);
        String[] credentials = decodedCredentials.split(":");

        if (credentials.length != 2) {
            throw new WebApplicationException(
              Response.status(Response.Status.BAD_REQUEST).header(SH.cors, "*").build());
        }

        Optional<User> authenticatedUser = userDao.authenticate(credentials[0], credentials[1]);

        if (!authenticatedUser.isPresent()) {
            throw new WebApplicationException(
              Response.status(Response.Status.UNAUTHORIZED).header(SH.cors, "*").build());
        }

        // Create a response with userId and token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", authenticatedUser.get().getId());
        jsonObject.put("token", userTokens.generateToken(authenticatedUser.get().getId()));

        return Response.ok().entity(jsonObject.toString()).header(SH.cors, "*").build();
    }

    @Path("/token")
    @DELETE
    public Response invalidateToken(
      @HeaderParam(SH.auth) final String token) {
        userTokens.clearToken(token);

        return Response.status(Response.Status.NO_CONTENT).header(SH.cors, "*").build();
    }
}
