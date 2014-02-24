package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectAssignmentDao;
import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.ProjectAssignment;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProjectAssignments(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return SH.corsResponseWithEntity(200, projectAssignmentDao.getAll());
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProjectAssignments(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      final ProjectAssignment projectAssignment) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        projectAssignmentDao.create(projectAssignment);
        return SH.corsResponse(201);
    }

    @PUT
    @Path("{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProjectAssignment(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("user_id") String id, //I added this. ProjectDao.read needs a string becuase you need to input the
      //the project name. This needs to be fixed. I just need it to compile though.
      ProjectAssignment projectAssignment) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project update = projectDao.read(id);
        if (update == null) {
            return SH.corsResponse(404);
        }
        projectAssignmentDao.update(projectAssignment);
        return SH.corsResponse(200);
    }
}
