package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectAssignmentDao;
import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.ProjectAssignment;
import ca.bcit.infosys.comp4911.domain.User;
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

@Path("/projects/{project_number}/assignments")
public class ProjectAssignmentResource {

    @EJB
    private UserTokens userTokens;

    @EJB
    private ProjectDao projectDao;

    @EJB
    private ProjectAssignmentDao projectAssignmentDao;

    @EJB
    private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProjectAssignments(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("project_number") int projectNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, projectAssignmentDao.getByProject(projectNumber));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProjectAssignments(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      final ProjectAssignment projectAssignment) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        projectAssignmentDao.create(projectAssignment,true);
        return SH.response(201);
    }

    @GET
    @Path("manager")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectManager(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
        @PathParam("project_number") int projectNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, projectAssignmentDao.getProjectManager(projectNumber));
    }

    @GET
    @Path("{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProjectAssignment(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("project_number") int projectNumber,
            @PathParam("user_id") int user_Id){

        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);
        return SH.responseWithEntity(200, projectAssignmentDao.getByUserAndProject(projectNumber, user_Id));
    }

    @PUT
    @Path("{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProjectIsManager(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("project_number") final Integer projectNumber,
            @PathParam("user_id") int id,
            final ProjectAssignment projectAssignment ) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        Project project = projectDao.read(projectNumber);
        if (project == null) {
            return SH.response(404);
        }

        projectAssignmentDao.update(projectAssignment);
        return SH.response(200);
    }


    /*

    @PUT
    @Path("{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProjectAssignment(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("project_number") int projectNumber,
      @PathParam("user_id") int id,
      final ProjectAssignment projectAssignment) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        Project project = projectDao.read(projectNumber);
        if (project == null) {
            return SH.response(404);
        }
        User user = userDao.read(id);
        if (user == null ) {
            return SH.response(404);
        }

        projectAssignmentDao.update(projectAssignment);
        return SH.response(200);
    }
    */
}
