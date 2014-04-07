package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Timesheet;
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

/**
 * Created by Graeme on 2/8/14.
 */
@Path("/timesheets")
public class TimesheetResource {

    @EJB
    private UserDao userDao;

    @EJB
    private TimesheetDao timesheetDao;

    @EJB
    private UserTokens userTokens;

    // we're going to have to mix this in with the timesheetRowDao as well
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTimesheets(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, timesheetDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTimesheet(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      final Timesheet timesheet) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);
        timesheetDao.create(timesheet,true);
        User user = userDao.read(userId);
        user.setTotalFlexTime(user.getTotalFlexTime() + timesheet.getOverTime());
        userDao.update(user);

        return SH.response(201);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimesheet(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        Timesheet timesheet = timesheetDao.read(id);
        if (timesheet == null) {
            return SH.response(404);
        }
        return SH.responseWithEntity(200, timesheet);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTimesheet(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") Integer id,
      final Timesheet timesheet) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        Timesheet update = timesheetDao.read(id);
        if (update == null) {
            return SH.response(404);
        }

        Timesheet current = timesheetDao.read(id);
        User user = userDao.read(userId);
        user.setTotalFlexTime(user.getTotalFlexTime() + (timesheet.getOverTime() - current.getOverTime()));
        timesheetDao.update(timesheet);
        return SH.response(200);
    }
}
