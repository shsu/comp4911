package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user/timesheets")
public class UserTimesheetsResource {

    @EJB
    private UserTokens userTokens;

    @EJB
    private TimesheetDao timesheetDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTimesheetsForUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken,
      @QueryParam("filter") final String timesheetsFilter) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        if (timesheetsFilter.equals("current")) {
            return SH.corsResponseWithEntity(200, timesheetDao.getByDate(
              SH.getCurrentWeek(), SH.getCurrentYear(), userId));
        } else {
            // TODO: Get timesheet for user.
            return SH.corsResponseWithEntity(200, timesheetDao.getAll());
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTimesheetForUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        //TODO: create a new timesheet, persist it and return the timesheet in the body.

        return SH.corsResponseWithEntity(201, null);
    }

    @Path("/rejected")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllRejectedTimesheetsForUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        return SH.corsResponseWithEntity(200, timesheetDao.getRejected(userId));
    }

    @Path("/to_be_approved")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTimesheetsNeedApprovalByUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        //TODO
        return SH.corsResponseWithEntity(200, null);
    }

}
