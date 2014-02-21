package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.PayRateDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.PayRate;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Created by Graeme on 2/8/14.
 */
@Path("/pay_rates")
public class PayRateResource {

    @EJB
    PayRateDao payRateDao;

    @EJB
    UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPayRates(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return SH.corsResponseWithEntity(200, payRateDao.getAllPayRates());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayRate(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      final PayRate payRate) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        payRateDao.create(payRate);
        return SH.corsResponse(201);
    }

    @GET
    @Path("{pay_level}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPayLevelRates(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("pay_level") String payLevelName) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        List<PayRate> payRates = payRateDao.getAllPayRatesByLevel(payLevelName);
        if (payRates == null) {
            return SH.corsResponse(404);
        }

        return SH.corsResponseWithEntity(200, payRates);
    }

    /**
     * Not sure if we need this endpoint actually
     */
    @PUT
    @Path("{pay_level}/{year}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePayRate(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("id") Integer id,
      final String payLevel,
      final Date year) {
        return null;
    }

    @GET
    @Path("{pay_level}/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePayRate(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("pay_level") final String payLevel,
      @PathParam("year") final String year) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        PayRate payRate = payRateDao.getPayRateByLevelAndYear(payLevel, year);
        if (payRate == null) {
            return SH.corsResponse(404);
        }

        return SH.corsResponseWithEntity(200, payRate);
    }
}
