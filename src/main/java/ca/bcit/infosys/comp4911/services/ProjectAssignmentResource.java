package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectAssignmentDao;
import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.ProjectAssignment;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Graeme on 2/11/14.
 */
@Path("/projects/{id}/assignments")
public class ProjectAssignmentResource {

    @EJB
    UserTokens userTokens;

    @EJB
    ProjectDao projectDao;

    @EJB
    ProjectAssignmentDao projectAssignmentDao;

    @EJB
    UserDao userDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProjectAssignments(
            @HeaderParam(SH.auth) final String token,
            final ProjectAssignment projectAssignment) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        projectAssignmentDao.create(projectAssignment);
        return SH.Response(201);
    }

    @PUT
    @Path("{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProjectAssignment(
            @HeaderParam(SH.auth) final String token,
            @PathParam("user_id") Integer id,
            ProjectAssignment projectAssignment) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project update = projectDao.read(id);
        if (update == null) {
            return SH.Response(404);
        }
        projectAssignmentDao.update(projectAssignment);
        return SH.Response(200);
    }
}
