package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
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
import javax.ws.rs.Path;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAuthenticatedUserInfo(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        return SH.corsResponseWithEntity(200, userDao.read(userId));
    }

    @Path("/token")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerAuth) {
        if (Strings.isNullOrEmpty(headerAuth)) {
            throw new WebApplicationException(SH.corsResponse(401));
        }

        String decodedCredentials = new String(
          BaseEncoding.base64().decode(headerAuth.substring("Basic ".length())), Charsets.UTF_8);
        String[] credentials = decodedCredentials.split(":");

        if (credentials.length != 2) {
            throw new WebApplicationException(SH.corsResponse(400));
        }

        for(String credential:credentials){
            if(Strings.isNullOrEmpty(credential)){
                throw new WebApplicationException(SH.corsResponse(400));
            }
        }

        return SH.corsResponseWithEntity(200, performLoginAndGenerateTokenInJSON(credentials[0], credentials[1]));
    }

    @Path("/token")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveToken(User user) {
        if (user == null || Strings.isNullOrEmpty(user.getUsername()) ||
          Strings.isNullOrEmpty(user.getPassword())) {
            throw new WebApplicationException(SH.corsResponse(400));
        }

        return SH.corsResponseWithEntity(200, performLoginAndGenerateTokenInJSON(user.getUsername(), user.getPassword()));
    }

    @Path("/token")
    @DELETE
    public Response invalidateToken(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        if (!userTokens.clearToken(token)) {
            throw new WebApplicationException(SH.corsResponse(401));
        }

        return SH.corsResponse(204);
    }

    @Path("/projects")
    @GET
    public Response retrieveAllProjectsAssignedToUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        // TODO: Get projects of user.
        return SH.corsResponseWithEntity(200,projectDao.getAll());
    }

    @Path("/work_packages")
    @GET
    public Response retrieveAllWorkPackagesAssignedToUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        // TODO: Get work packages of user.
        return SH.corsResponseWithEntity(200,workPackageDao.getAll());
    }

    private String performLoginAndGenerateTokenInJSON(final String username, final String password) {
        Optional<User> authenticatedUser = userDao.authenticate(username, password);

        if (!authenticatedUser.isPresent()) {
            throw new WebApplicationException(SH.corsResponse(401));
        }

        // Create a response with userId and token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", authenticatedUser.get().getId());
        jsonObject.put("token", userTokens.generateToken(authenticatedUser.get().getId()));
        return jsonObject.toString();
    }
}
