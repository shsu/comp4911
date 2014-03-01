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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/projects")
public class ProjectResource {

    @EJB
    private ProjectDao projectDao;

    @EJB
    private WorkPackageAssignmentDao workPackageAssignmentDao;

    @EJB
    private ProjectAssignmentDao projectAssignmentDao;

    @EJB
    private UserDao userDao;

    @EJB
    private UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProjects(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, projectDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProject(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      final Project project) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        projectDao.create(project);
        return SH.response(201);
    }

    @GET
    @Path("{project_number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProject(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("project_number") int projectNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        Project project = projectDao.read(projectNumber);
        if (project == null) {
            return SH.response(404);
        }

        return SH.responseWithEntity(200, project);
    }

    @PUT
    @Path("{project_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProject(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("project_number") int projectNumber,
      final Project Project) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        Project check = projectDao.read(projectNumber);
        if (check == null) {
            return SH.response(404);
        }

        projectDao.update(Project);
        return SH.response(200);
    }

    @GET
    @Path("{project_number}/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersForProject(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("project_number") final int projectNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        Project check = projectDao.read(projectNumber);
        if (check == null) {
            return SH.response(404);
        }

        return SH.responseWithEntity(200, projectAssignmentDao.getAllUsers(projectNumber));
    }
}
