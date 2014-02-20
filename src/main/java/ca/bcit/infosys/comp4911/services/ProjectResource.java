package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectAssignmentDao;
import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.access.WorkPackageAssignmentDao;
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
 * Created by Graeme on 2/8/14.
 */
@Path("/projects")
public class ProjectResource {

    @EJB
    ProjectDao projectDao;

    @EJB
    WorkPackageAssignmentDao workPackageAssignmentDao;

    @EJB
    ProjectAssignmentDao projectAssignmentDao;

    @EJB
    UserDao userDao;

    @EJB
    UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProjects(
      @HeaderParam(SH.auth) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return SH.Response(200, projectDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProject(
      @HeaderParam(SH.auth) final String token,
      final Project project) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        projectDao.create(project);
        return SH.Response(201);
    }

    @GET
    @Path("{project_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProject(
            @HeaderParam(SH.auth) final String token,
            @PathParam("project_id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if (project == null) {
            return SH.Response(404);
        }

        return SH.Response(200, project);
    }

    @PUT
    @Path("{project_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProject(
      @HeaderParam(SH.auth) final String token,
      @PathParam("project_id") Integer id,
      final Project Project) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project check = projectDao.read(id);
        if (check == null) {
            return SH.Response(404);
        }

        projectDao.update(Project);
        return SH.Response(200);
    }

    @GET
    @Path("{project_id}/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersForProject(
            @HeaderParam(SH.auth) final String token,
            @PathParam("project_id") final Integer id)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project check = projectDao.read(id);
        if(check == null) {
            return SH.Response(404);
        }

        return SH.Response(200, projectAssignmentDao.getAllUsers(id));
    }
}
