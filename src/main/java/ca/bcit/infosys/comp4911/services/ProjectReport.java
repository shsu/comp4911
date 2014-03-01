package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.helper.SH;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Dynamically creates report and returns it.
 */
@Path("/reports")
public class ProjectReport {

    @EJB
    UserTokens userTokens;

    @Path("{id}")
    public Response getProjectReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final String projectId){
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        // No need for actual Entity because it is not actually stored in DB
        JSONObject report = new JSONObject();

        return SH.responseWithEntity(200, report);
    }
}
