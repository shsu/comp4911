package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.access.TimesheetRowDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.domain.TimesheetRow;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/user/timesheets")
public class UserTimesheetsResource {

    @EJB
    private UserTokens userTokens;

    @EJB
    private TimesheetDao timesheetDao;

    @EJB
    private TimesheetRowDao timesheetRowDao;

    @EJB
    private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTimesheetsForUser(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @QueryParam(SH.FILTER) final String filter,
            @QueryParam(SH.YEAR) final int year,
            @QueryParam(SH.WEEK) final int week) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        if (filter != null) {
            if (filter.equals("current")) {
                List<Timesheet> timesheets = timesheetDao.getByDate(SH.getCurrentWeek(), SH.getCurrentYear(), userId);

                if(timesheets.isEmpty())
                {
                    return SH.responseWithEntity(200, createCurrentWeekTimesheet(userId));
                }

                return SH.responseWithEntity(200, timesheets.get(0));
            }
        }
        if(year != 0) {
            List<Timesheet> timesheets = timesheetDao.getByDate(week, year, userId);
            if(timesheets.isEmpty())
            {
                return SH.response(404);
            }

            return SH.responseWithEntity(200, timesheets.get(0));
        }
        return SH.responseWithEntity(200, timesheetDao.getAllByUser(userId));
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

        timesheetDao.update(timesheet);
        return SH.response(200);
    }

    @Path("/rejected")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllRejectedTimesheetsForUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        return SH.responseWithEntity(200, timesheetDao.getRejected(userId));
    }

    @Path("/to_be_approved")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTimesheetsNeedApprovalByUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        //TODO
        return SH.responseWithEntity(200, null);
    }

    private Timesheet createCurrentWeekTimesheet(int userId) {
        User user = userDao.read(userId);

        if(user.getDefaultTimesheetID() != -1) {
            Timesheet defaultSheet = timesheetDao.read(user.getDefaultTimesheetID());
            return createDefaultTimesheet(defaultSheet, userId);
        } else {
            return createBlankTimesheet(userId);
        }
    }

    private Timesheet createDefaultTimesheet(Timesheet defaultSheet, int userId) {
        List<TimesheetRow> rows = new ArrayList<TimesheetRow>();

        for (TimesheetRow row : defaultSheet.getTimesheetRows()) {
            TimesheetRow temp = new TimesheetRow(
                    row.getProjectNumber(),
                    row.getWorkPackageNumber(),
                    row.getMonday(),
                    row.getTuesday(),
                    row.getWednesday(),
                    row.getThursday(),
                    row.getFriday(),
                    row.getSaturday(),
                    row.getSunday(),
                    row.getNote()
            );
            timesheetRowDao.create(temp,true);
            rows.add(temp);
        }
        Timesheet newTimesheet = new Timesheet(
                userId,
                rows,
                SH.getCurrentWeek(),
                SH.getCurrentYear(),
                defaultSheet.getOverTime(),
                defaultSheet.isApproved(),
                defaultSheet.isSigned()
        );
        timesheetDao.create(newTimesheet,true);
        return newTimesheet;
    }

    private Timesheet createBlankTimesheet(int userId) {
        List<TimesheetRow> rows = new ArrayList<TimesheetRow>();

        for (int i = 0; i < 5; ++i) {
            TimesheetRow temp = new TimesheetRow(
                    0, "", 0, 0, 0, 0, 0, 0, 0, ""
            );
            rows.add(temp);
        }
        Timesheet newTimesheet = new Timesheet(
                userId,
                rows,
                SH.getCurrentWeek(),
                SH.getCurrentYear(),
                0, false, false
        );
        timesheetDao.create(newTimesheet,true);
        return newTimesheet;
    }

}
