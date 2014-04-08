package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.TimesheetRowDao;
import ca.bcit.infosys.comp4911.access.WorkPackageAssignmentDao;
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
import java.util.List;

@Path("/work_packages")
public class WorkPackageResource {

    @EJB
    private WorkPackageDao workPackageDao;

    @EJB
    private WorkPackageStatusReportDao workPackageStatusReportDao;

    @EJB
    private WorkPackageAssignmentDao workPackageAssignmentDao;

    @EJB
    private UserTokens userTokens;

    @EJB
    private TimesheetRowDao timesheetRowDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllWorkPackages(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, workPackageDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWorkPackage(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            final WorkPackage workPackage) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        if(!workPackageDao.doesParentExist(workPackage.getWorkPackageNumber())){
            return SH.responseWithEntity(409, "You must first create the appropriate parent work package.");
        }

        if(timesheetRowDao.isParentLeaf(workPackage.getWorkPackageNumber()))
        {
            return SH.responseWithEntity(409, "The Parent of this Work Package already contains Timesheets.");
        }
        else if(workPackageDao.isParentLeaf(workPackage.getWorkPackageNumber()))
        {
            return SH.responseWithEntity(409, "The Parent of this Work Package already contains an initial estimate");
        }

        workPackageDao.create(workPackage,false);
        return SH.response(201);
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

    @PUT
    @Path("{workpackage_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackage(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("workpackage_number") String workPackageNumber,
      WorkPackage workPackage) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage workPackageUpdate = workPackageDao.read(workPackageNumber);
        if (workPackageUpdate == null) {
            return SH.response(404);
        }

        return SH.responseWithEntity(200, workPackageDao.update(workPackage));
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

    @GET
    @Path("project/{project_number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllWorkPackagesByProject(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("project_number") int projectNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, workPackageDao.getAllByProject(projectNumber));
    }

    @GET
    @Path("{workpackage_number}/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWorkPackageAssignments(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("workpackage_number") String workPackageNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        List<String> workPackageChildren = workPackageDao.getWPChildren(workPackageNumber);

        return SH.responseWithEntity(200,
                workPackageAssignmentDao.getAllUsersByMultipleWorkPackageNumber(workPackageChildren));
    }
}
