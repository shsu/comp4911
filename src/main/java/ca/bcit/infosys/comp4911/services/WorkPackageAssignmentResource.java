package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.WorkPackageAssignmentDao;
import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.domain.WorkPackageAssignment;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Graeme on 2/11/14.
 */
@Path("/work_packages/{id}/assignments")
public class WorkPackageAssignmentResource {
    @EJB
    UserTokens userTokens;

    @EJB
    WorkPackageAssignmentDao workPackageAssignmentDao;

    @EJB
    WorkPackageDao workPackageDao;

    @EJB
    UserDao userDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWorkPackageAssignment(
            @HeaderParam(SH.auth) final String token,
            @PathParam("id") Integer id,
            WorkPackageAssignment workPackageAssignment) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackageAssignment update = workPackageAssignmentDao.read(id);
        if (update == null) {
            return SH.Response(404);
        }

        workPackageAssignmentDao.create(workPackageAssignment);
        return SH.Response(201);
    }

    @PUT
    @Path("{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackageAssignment(
            @HeaderParam(SH.auth) final String token,
            @PathParam("user_id") Integer id,
            @PathParam("id") Integer wpId,
            WorkPackageAssignment workPackageAssignment) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(wpId);
        if (workPackage == null) {
            return SH.Response(404);
        }

        User user = userDao.read(id);
        if (user == null) {
            return SH.Response(404);
        }

        // See WorkPackageAssignmentDao for explanation
        List<WorkPackageAssignment> wpAssignmentList =
                workPackageAssignmentDao.getByUserAndWorkPackage(workPackage, user);
        if (wpAssignmentList == null) {
            return SH.Response(404);
        }

        workPackageAssignmentDao.update(workPackageAssignment);
        return SH.Response(200);
    }

}
