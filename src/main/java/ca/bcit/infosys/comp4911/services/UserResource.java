package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


@Path("/user")
public class UserResource {

    @EJB
    private UserTokens userTokens;

    @EJB
    private UserDao userDao;

    @EJB
    private ProjectDao projectDao;

    @EJB
    private WorkPackageDao workPackageDao;

    @EJB
    private WorkPackageAssignmentDao workPackageAssignmentDao;
    @EJB
    private TimesheetDao timesheetDao;

    @EJB
    private TimesheetRowDao timesheetRowDao;

    @EJB
    private ProjectAssignmentDao projectAssignmentDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUserProfile(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, userDao.read(userId));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserProfile(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      final User user) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        if(user.getId() != userId){
            SH.responseBadRequest("Unable to modify the profile of a user other than yourself. Please use the /users endpoint.");
        }

        return SH.responseWithEntity(200, userDao.update(user));
    }

    @Path("/token")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerAuth) {
        if (Strings.isNullOrEmpty(headerAuth)) {
            throw new WebApplicationException(Response.status(401).header("WWW-Authenticate","Basic realm=\"comp4911\"").build());
        }

        String decodedCredentials = new String(
          BaseEncoding.base64().decode(headerAuth.substring("Basic ".length())), Charsets.UTF_8);
        String[] credentials = decodedCredentials.split(":");

        if (credentials.length != 2) {
            SH.responseBadRequest("Username/Password Missing.");
        }

        for (String credential : credentials) {
            if (Strings.isNullOrEmpty(credential)) {
                SH.responseBadRequest("Username/Password Missing.");
            }
        }

        return SH.responseWithEntity(200, performLoginAndGenerateTokenInJSON(credentials[0], credentials[1]));
    }


    @Path("/token")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(User user) {
        if (user == null || Strings.isNullOrEmpty(user.getUsername()) ||
          Strings.isNullOrEmpty(user.getPassword())) {
            SH.responseBadRequest("Username/Password Missing.");
        }

        return SH.responseWithEntity(200, performLoginAndGenerateTokenInJSON(user.getUsername(), user.getPassword()));

    }

    @Path("/token")
    @DELETE
    public Response invalidateToken(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        userTokens.clearToken(headerToken, queryToken);

        return SH.response(204);
    }

    @Path("/permissions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPermissions(
        @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
        @QueryParam(SH.TOKEN_STRING) final String queryToken) {

        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        JSONArray permissionArray = new JSONArray();
        JSONObject temp = new JSONObject();

        if(userDao.read(userId).isHR()){
            temp.put("name", "Hr");
            permissionArray.put(temp);
        }
        if(projectAssignmentDao.isProjectManager(userId)) {
            temp = new JSONObject();
            temp.put("name", "ProjectManager");
            permissionArray.put(temp);
        }
        if(userDao.isSupervisor(userId)) {
            temp = new JSONObject();
            temp.put("name", "Supervisor");
            permissionArray.put(temp);
        }
        if(userDao.isTimesheetApprover(userId)){
            temp = new JSONObject();
            temp.put("name", "TimesheetApprover");
            permissionArray.put(temp);
        }
        if(workPackageAssignmentDao.isResponsibleEngineer(userId)) {
            temp = new JSONObject();
            temp.put("name", "ResponsibleEngineer");
            permissionArray.put(temp);
        }
        return SH.responseWithEntity(200, permissionArray.toString());
    }

    @Path("/projects")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProjectsAssignedToUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, projectDao.getAllByUser(userId));
    }

    @Path("/projects/managed")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllUserManagedProjects(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, projectDao.getAllManagedByUser(userId));
    }



    @Path("/work_packages")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllWorkPackagesAssignedToUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @QueryParam("filter") final String filter) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        if(filter != null && filter.equals("responsibleEngineer")) {
            return SH.responseWithEntity(200, workPackageDao.getAllByEngineer(userId));
        }
        return SH.responseWithEntity(200, workPackageDao.getAllByUser(userId));
    }

    @Path("/peons")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPeons(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, userDao.getAllPeons(userId));
    }

    private String performLoginAndGenerateTokenInJSON(final String username, final String password) {
        Optional<User> authenticatedUser = userDao.authenticate(username, password);

        if (!authenticatedUser.isPresent()) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        // Create a response with userId and token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", authenticatedUser.get().getId());
        jsonObject.put("token", userTokens.generateToken(authenticatedUser.get().getId()));
        return jsonObject.toString();
    }

}
