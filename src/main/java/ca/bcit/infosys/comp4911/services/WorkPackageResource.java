package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.access.WorkPackageStatusReportDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

@Path("/work_packages")
public class WorkPackageResource {

    @EJB
    private WorkPackageDao workPackageDao;

    @EJB
    private WorkPackageStatusReportDao workPackageStatusReportDao;

    @EJB
    private UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllWorkPackages(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, workPackageDao.getAll());
    }

    @GET
    @Path("{workpackage_number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveWorkPackage(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("workpackage_number") String workpackageNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage workPackage = workPackageDao.read(workpackageNumber);
        if (workPackage == null) {
            return SH.response(404);
        }
        return SH.responseWithEntity(200, workPackage);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayRate(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      final WorkPackage workPackage) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        workPackageDao.create(workPackage);
        return SH.response(201);
    }

    @PUT
    @Path("{workpackage_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackage(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("workpackage_number") String workpackageNumber,
      final WorkPackage workPackage) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage workPackageUpdate = workPackageDao.read(workpackageNumber);
        if (workPackageUpdate == null) {
            return SH.response(404);
        }

        workPackageDao.update(workPackage);
        return SH.response(200);
    }

    @DELETE
    @Path("{workpackage_number}")
    public Response deleteWorkPackage(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("workpackage_number") String workpackageNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage workPackage = workPackageDao.read(workpackageNumber);
        if (workPackage == null) {
            return SH.response(404);
        }

        workPackageDao.delete(workPackage);
        return SH.response(204);
    }
}
