package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectAssignmentDao;
import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.access.WorkPackageAssignmentDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Project;
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
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return SH.corsResponseWithEntity(200, projectDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProject(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      final Project project) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        projectDao.create(project);
        return SH.corsResponse(201);
    }

    @GET
    @Path("{project_number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProject(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("project_number") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if (project == null) {
            return SH.corsResponse(404);
        }

        return SH.corsResponseWithEntity(200, project);
    }

    @PUT
    @Path("{project_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProject(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("project_number") Integer id,
      final Project Project) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project check = projectDao.read(id);
        if (check == null) {
            return SH.corsResponse(404);
        }

        projectDao.update(Project);
        return SH.corsResponse(200);
    }

    @GET
    @Path("{project_number}/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersForProject(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String token,
      @PathParam("project_number") final Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project check = projectDao.read(id);
        if (check == null) {
            return SH.corsResponse(404);
        }

        return SH.corsResponseWithEntity(200, projectAssignmentDao.getAllUsers(id));
    }
}
