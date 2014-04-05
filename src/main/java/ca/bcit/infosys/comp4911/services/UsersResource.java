package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.*;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Path("/users")
public class UsersResource {

    @EJB
    private UserDao userDao;

    @EJB
    private UserTokens userTokens;

    @EJB
    private ProjectAssignmentDao projectAssignmentDao;

    @EJB
    private ProjectDao projectDao;

    @EJB
    private WorkPackageAssignmentDao workPackageAssignmentDao;

    @EJB
    private WorkPackageDao workPackageDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllUsers(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);
        return SH.responseWithEntity(200, userDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      final User user) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        userDao.create(user, false);
        return SH.response(201);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") final Integer id) {

        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        User user = userDao.read(id);
        if (user == null) {
            return SH.response(404);
        }

        return SH.responseWithEntity(200, user);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") final Integer id,
      final User user) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        User check = userDao.read(id);
        if (check == null) {
            return SH.response(404);
        }

        userDao.update(user);

        return SH.responseWithEntity(200, userDao.read(user.getId()));
    }

    @GET
    @Path("{id}/projects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProjects(
        @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
        @QueryParam(SH.TOKEN_STRING) final String queryToken,
        @PathParam("id") final Integer id) {

        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        User check = userDao.read(id);
        if(check == null){
            return SH.response(404);
        }

        List<ProjectAssignment> projectsAssignedToSelectedUser = projectAssignmentDao.getAllByUserId(id);
        int numOfProjectAssignmentsReturned = projectsAssignedToSelectedUser.size();
        List<Integer> projectIds = new ArrayList<Integer>();

        for(int i = 0; i < numOfProjectAssignmentsReturned; i++){
            projectIds.add(i, projectsAssignedToSelectedUser.get(i).getProjectNumber());
        }

        return SH.responseWithEntity(200, projectDao.getProjectsByIds(projectIds));

    }

    @GET
    @Path("{id}/workpackages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWorkPackages(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final Integer id) {

        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        User check = userDao.read(id);
        if(check == null){
            return SH.response(404);
        }

        List<WorkPackageAssignment> wpsAssignedToSelectedUser = workPackageAssignmentDao.getAllByUserId(id);
        int numOfWPsAssignmentsReturned = wpsAssignedToSelectedUser.size();
        List<String> wpNumbers = new ArrayList<String>();

        for(int i = 0; i < numOfWPsAssignmentsReturned; i++){
            wpNumbers.add(i, wpsAssignedToSelectedUser.get(i).getWorkPackageNumber());
        }

        return SH.responseWithEntity(200, workPackageDao.getWPsByWorkPackageNumbers(wpNumbers));

    }

}
