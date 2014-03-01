package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    private TimesheetDao timesheetDao;

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

        userDao.update(user);
        return SH.response(200);
    }

    @Path("/token")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerAuth) {
        if (Strings.isNullOrEmpty(headerAuth)) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        String decodedCredentials = new String(
          BaseEncoding.base64().decode(headerAuth.substring("Basic ".length())), Charsets.UTF_8);
        String[] credentials = decodedCredentials.split(":");

        if (credentials.length != 2) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        for (String credential : credentials) {
            if (Strings.isNullOrEmpty(credential)) {
                throw new WebApplicationException(SH.response(400));
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
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
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

    @Path("/projects")
    @GET
    public Response retrieveAllProjectsAssignedToUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, projectDao.getAllByUser(userId));
    }

    @Path("/work_packages")
    @GET
    public Response retrieveAllWorkPackagesAssignedToUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, workPackageDao.getAllByUser(userId));
    }

    @Path("/timesheets")
    @GET
    public Response retrieveAllTimesheetsForUser(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @QueryParam(SH.FILTER) final String filter) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        if (filter != null) {
            if (filter.equals("current")) {
                Timesheet timesheet = timesheetDao.getByDate(SH.getCurrentWeek(), SH.getCurrentYear(), userId);
                return SH.responseWithEntity(200, timesheet);
            }
            if (filter.equals("default")) {
                Timesheet timesheet = timesheetDao.getByDate(0, 0, userId);
                return SH.responseWithEntity(200, timesheet);
            }
        }

        return SH.responseWithEntity(200, timesheetDao.getAllByUser(userId));
    }

    // Create Timesheet for Authenticated User
    @Path("/timesheets")
    @POST
    public Response createTimesheet(
        @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
        @QueryParam(SH.TOKEN_STRING) final String queryToken,
        final Timesheet timesheet) {
            int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

            timesheetDao.create(timesheet);
            return SH.response(201);
    }

    @Path("/timesheets/to_approve")
    public Response getAllTimesheetsToApprove(
         @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
         @QueryParam(SH.TOKEN_STRING) final String queryToken,
         final Timesheet timesheet) {
            int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

            return SH.responseWithEntity(200,timesheetDao.getAllTimesheetsToApprove(userId));
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
