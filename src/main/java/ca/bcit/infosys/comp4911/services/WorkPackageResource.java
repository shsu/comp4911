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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Graeme on 2/8/14.
 */
@Path("/work_packages")
public class WorkPackageResource {

    @EJB
    WorkPackageDao workPackageDao;

    @EJB
    WorkPackageStatusReportDao workPackageStatusReportDao;

    @EJB
    UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllWorkPackages(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return SH.corsResponseWithEntity(200, workPackageDao.getAll());
    }

    @GET
    @Path("{workpackage_number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveWorkPackage(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("workpackage_number") String workpackageNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(workpackageNumber);
        if (workPackage == null) {
            return SH.corsResponse(404);
        }
        return SH.corsResponseWithEntity(200, workPackage);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayRate(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      WorkPackage workPackage) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        workPackageDao.create(workPackage);
        return SH.corsResponse(201);
    }

    @PUT
    @Path("{workpackage_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackage(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("workpackage_number") String workpackageNumber,
      WorkPackage workPackage) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackageUpdate = workPackageDao.read(workpackageNumber);
        if (workPackageUpdate == null) {
            return SH.corsResponse(404);
        }

        workPackageDao.update(workPackage);
        return SH.corsResponse(200);
    }

    @DELETE
    @Path("{workpackage_number}")
    public Response deleteWorkPackage(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("workpackage_number") String workpackageNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(workpackageNumber);
        if (workPackage == null) {
            return SH.corsResponse(404);
        }

        workPackageDao.delete(workPackage);
        return SH.corsResponse(204);
    }
}
