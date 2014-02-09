package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.access.WorkPackageAssignmentDao;
import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.domain.WorkPackageAssignment;
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
            @HeaderParam("Authorization") final String token)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return Response.ok().entity(projectDao.getAll()).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProject(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if(project == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        return Response.ok().entity(project).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayRate(
            @HeaderParam("Authorization") final String token,
            Project project)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        projectDao.create(project);

        return Response.status(201).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProject(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id,
            Project Project)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project update = projectDao.read(id);
        if(update == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }
        projectDao.update(Project);
        return Response.ok().header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProject(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if(project == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        projectDao.delete(project);
        return Response.ok().header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @POST
    @Path("{id}/assignments")
    public Response retrieveProjectAssignments(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id,
            JSONObject obj)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if(project == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        // At the moment only taking in a userid, but going to need more info than this.
        // workPackageAssignmentDao.create();

        return Response.status(201).header(SH.cors, "*")
                .header(SH.auth, token).build();
    }

    @DELETE
    @Path("{id}/assignments/{user_id}")
    public Response deleteAssignment(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id,
            @PathParam("user_id") Integer userId)
    {
        int authId = userTokens.verifyTokenAndReturnUserID((token));

        Project project = projectDao.read(id);
        if(project == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        User user = userDao.read(userId);
        if(user == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .header(SH.auth, token).build();
        }

        // remove user from workPackageAssigment
        // workPackageDao.delete(workPackageAssignment)

        return Response.status(204).header(SH.cors, "*")
                .header(SH.auth,token).build();
    }
}
