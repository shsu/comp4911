package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @QueryParam("filter") final String filter) {
        int userId = 1;
        if (filter.equals("manager")) {
            // get managers
            // return
        }
        return SH.corsResponseWithEntity(200, userDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      final User user) {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        // Need to do some Role Checks right here
        // Throw a 401 or/and 403 otherwise

        userDao.create(user);
        return SH.corsResponse(201);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("id") final Integer id) {

        int userId = userTokens.verifyTokenAndReturnUserID(token);

        User user = userDao.read(id);
        if (user == null) {
            return SH.corsResponse(404);
        }

        return SH.corsResponseWithEntity(200, user);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("id") final Integer id,
      final User user) {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        User check = userDao.read(id);
        if (check == null) {
            return SH.corsResponse(404);
        }

        userDao.update(user);
        return SH.corsResponse(200);
    }
}
