package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.PayLevelDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.PayLevel;
import ca.bcit.infosys.comp4911.helper.SH;

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
      @HeaderParam(SH.auth) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return Response.ok().entity(payLevelDao.getAll()).header(SH.cors, "*")
          .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePayLevel(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        PayLevel payLevel = payLevelDao.read(id);
        if (payLevel == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }

        return Response.ok().entity(payLevel).header(SH.cors, "*")
          .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayLevel(
      @HeaderParam(SH.auth) final String token, PayLevel payLevel) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        payLevelDao.create(payLevel);
        return Response.status(201).header(SH.cors, "*")
          .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePayLevel(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id, PayLevel payLevel) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        PayLevel update = payLevelDao.read(id);
        if (update == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }

        payLevelDao.update(payLevel);
        return Response.ok().header(SH.cors, "*")
          .build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePayLevel(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        PayLevel payLevel = payLevelDao.read(id);
        if (payLevel == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }

        payLevelDao.delete(payLevel);
        return Response.ok().header(SH.cors, "*")
          .build();
    }

}
