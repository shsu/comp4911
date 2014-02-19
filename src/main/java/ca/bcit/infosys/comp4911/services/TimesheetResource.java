package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.persistence.Entity;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Graeme on 2/8/14.
 */
@Path("/timesheets")
public class TimesheetResource {

    @EJB
    TimesheetDao timesheetDao;

    @EJB
    UserTokens userTokens;

    // we're going to have to mix this in with the timesheetRowDao as well
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTimesheets(
      @HeaderParam(SH.auth) final String token,
      @QueryParam("filter") final String filter) {
        int userId = 1; //userTokens.verifyTokenAndReturnUserID((token));

        if(filter.equals("current")) {
            Timesheet timesheet = timesheetDao.getByDate(SH.getCurrentWeek(), SH.getCurrentYear(),userId);
            return SH.Response(200, timesheet);
        }
        if(filter.equals("default")) {
            //Timesheet timesheet = timesheetDao.getByDate(54, userId);  Not sure where we'll store the default sheet
        }

        // if no filter return all the timesheets
        return SH.Response(200, timesheetDao.getAll());
    }

    /** Should we just use this method for retrieving the default */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimesheet(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Timesheet timesheet = timesheetDao.read(id);
        if (timesheet == null) {
            return SH.Response(404);
        }
        return SH.Response(200, timesheet);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTimesheet(
      @HeaderParam(SH.auth) final String token,
      @PathParam("id") Integer id, Timesheet timesheet) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        Timesheet update = timesheetDao.read(id);
        if (update == null) {
            return SH.Response(404);
        }

        timesheetDao.update(timesheet);
        return SH.Response(200);
    }
}
