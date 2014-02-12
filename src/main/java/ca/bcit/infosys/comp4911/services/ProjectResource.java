package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.access.WorkPackageAssignmentDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Project;
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
    UserDao userDao;

    @EJB
    UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProjects(
      @HeaderParam(SH.auth) final String token) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return Response.ok().entity(projectDao.getAll()).header(SH.cors, "*")
          .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProject(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if (project == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }

        return Response.ok().entity(project).header(SH.cors, "*")
          .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayRate(
      @HeaderParam(SH.auth) final String token,
      Project project) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        projectDao.create(project);

        return Response.status(201).header(SH.cors, "*")
          .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProject(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id,
      Project Project) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project update = projectDao.read(id);
        if (update == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }
        projectDao.update(Project);
        return Response.ok().header(SH.cors, "*")
          .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProject(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if (project == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }

        projectDao.delete(project);
        return Response.ok().header(SH.cors, "*")
          .build();
    }

    @POST
    @Path("{id}/assignments")
    public Response retrieveProjectAssignments(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id,
      JSONObject obj) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if (project == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }

        // At the moment only taking in a userid, but going to need more info than this.
        // workPackageAssignmentDao.create();

        return Response.status(201).header(SH.cors, "*")
          .build();
    }

    @DELETE
    @Path("{id}/assignments/{user_id}")
    public Response deleteAssignment(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id,
      @PathParam("user_id") Integer userId) {
        int authId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if (project == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }

        User user = userDao.read(userId);
        if (user == null) {
            return Response.status(404).header(SH.cors, "*")
              .build();
        }

        // remove user from workPackageAssigment
        // workPackageDao.delete(workPackageAssignment)

        return Response.status(204).header(SH.cors, "*")
          .build();
    }
}