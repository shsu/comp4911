package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.PayLevelDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.PayLevel;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Graeme on 2/8/14.
 */
@Path("/pay_rates")
public class PayLevelResource {

    @EJB
    PayLevelDao payLevelDao;

    @EJB
    UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPayLevels(
            @HeaderParam("Authorization") final String token)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return Response.ok().entity(payLevelDao.getAll()).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePayLevel(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        PayLevel payLevel = payLevelDao.read(id);
        if(payLevel == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        return Response.ok().entity(payLevel).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayLevel(
            @HeaderParam("Authorization") final String token,
            PayLevel payLevel)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        payLevelDao.create(payLevel);
        return Response.status(201).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePayLevel(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id,
            PayLevel payLevel)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        PayLevel update = payLevelDao.read(id);
        if(update == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        payLevelDao.update(payLevel);
        return Response.ok().header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePayLevel(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        PayLevel payLevel = payLevelDao.read(id);
        if(payLevel == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        payLevelDao.delete(payLevel);
        return Response.ok().header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

}
