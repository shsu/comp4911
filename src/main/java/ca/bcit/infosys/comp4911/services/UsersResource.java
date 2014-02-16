package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersResource {

    @EJB
    UserDao userDao;

    @EJB
    UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllUsers(
      @HeaderParam(SH.auth) final String token) {
        int userId = 1;

        return SH.Response(200, userDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(
      @HeaderParam(SH.auth) final String token,
      final User user) {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        // Need to do some Role Checks right here
        // Throw a 401 or/and 403 otherwise

        userDao.create(user);
        return SH.Response(201);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUser(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") final Integer id) {

        int userId = userTokens.verifyTokenAndReturnUserID(token);

        User user = userDao.read(id);
        if (user == null) {
            return SH.Response(404);
        }

        return SH.Response(200, user);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") final Integer id,
      final User user) {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        User check = userDao.read(id);
        if (check == null) {
            return SH.Response(404);
        }

        userDao.update(user);
        return SH.Response(200);
    }
}
