package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.access.WorkPackageAssignmentDao;
import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.ProjectAssignment;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.domain.WorkPackageAssignment;
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
import java.util.List;

@Path("/work_packages/{id}/assignments")
public class WorkPackageAssignmentResource {
    @EJB
    private UserTokens userTokens;

    @EJB
    private WorkPackageAssignmentDao workPackageAssignmentDao;

    @EJB
    private WorkPackageDao workPackageDao;

    @EJB
    private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWorkPackageAssignments(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") String id,
      @QueryParam("filter") String filter) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        if(filter != null && filter.equals("active")) {
            return SH.responseWithEntity(200, workPackageAssignmentDao.getAllActiveUsersByWorkPackageNumber(id));
        }

        return SH.responseWithEntity(200, workPackageAssignmentDao.getAllUsersByWorkPackageNumber(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWorkPackageAssignment(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") String id,
      final WorkPackageAssignment workPackageAssignment) {
            int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

            workPackageAssignmentDao.create(workPackageAssignment,true);
            return SH.response(201);
    }

    @GET
    @Path("{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkPackageAssignmentByUserAndWorkPackage(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("user_id") Integer userId,
            @PathParam("id") String wpNumber) {
        userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        User user = userDao.read(userId);
        if (user == null) {
            return SH.response(404);
        }

        WorkPackage wp = workPackageDao.read(wpNumber);
        if (wp == null) {
            return SH.response(404);
        }

        return SH.responseWithEntity(200, workPackageAssignmentDao.getByUserAndWorkPackage(wpNumber, userId));
    }

    @PUT
    @Path("{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackageAssignment(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("user_id") Integer user_id,
      @PathParam("id") String wpId,
      final WorkPackageAssignment workPackageAssignment) {
        userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage workPackage = workPackageDao.read(wpId);
        if (workPackage == null) {
            return SH.response(404);
        }

        User user = userDao.read(user_id);
        if (user == null) {
            return SH.response(404);
        }

        // See WorkPackageAssignmentDao for explanation
        List<WorkPackageAssignment> wpAssignmentList =
          workPackageAssignmentDao.getByUserAndWorkPackage(wpId, user_id);
        if (wpAssignmentList == null) {
            return SH.response(404);
        }

        workPackageAssignmentDao.update(workPackageAssignment);
        return SH.response(200);
    }

}
