package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.User;

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
    public Response retrieveAll(
            @HeaderParam("Authorization") final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        return Response.ok().entity(userDao.getAll()).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(
            @HeaderParam("Authorization") final String token,
            final User user) {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        // Need to do some Role Checks right here
        // Throw a 401 or/and 403 otherwise

        userDao.create(user);
        return Response.status(201).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUser(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") final Integer id) {

        int userId = userTokens.verifyTokenAndReturnUserID(token);

        User user = userDao.read(id);
        if(user == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        return Response.ok().entity(user).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") final Integer id,
            final User user)
    {
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        User userUpdate = userDao.read(id);
        if(userUpdate == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        userDao.update(user);
        return Response.ok().header(SH.cors, "*")
                .header(SH.auth, token).build();
    }
}
