package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.access.WorkPackageStatusReportDao;
import ca.bcit.infosys.comp4911.access.WorkPackageStatusReportDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.domain.WorkPackageStatusReport;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.client.Entity;
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
      @HeaderParam(SH.auth) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return SH.Response(200, workPackageDao.getAll());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveWorkPackage(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(id);
        if (workPackage == null) {
            return SH.Response(404);
        }
        return SH.Response(200, workPackage);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayRate(
      @HeaderParam(SH.auth) final String token,
      WorkPackage workPackage) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        workPackageDao.create(workPackage);
        return SH.Response(201);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackage(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id,
      WorkPackage workPackage) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackageUpdate = workPackageDao.read(id);
        if (workPackageUpdate == null) {
            return SH.Response(404);
        }

        workPackageDao.update(workPackage);
        return SH.Response(200);
    }

    @DELETE
    @Path("{id}")
    public Response deleteWorkPackage(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(id);
        if (workPackage == null) {
            return SH.Response(404);
        }

        workPackageDao.delete(workPackage);
        return SH.Response(204);
    }
}
