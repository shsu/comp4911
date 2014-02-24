package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user/timesheets")
public class UserTimesheets {

    @EJB
    private UserTokens userTokens;

    @EJB
    private TimesheetDao timesheetDao;

    @GET
    public Response retrieveAllTimesheetsOfAuthenticatedUser(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN) final String queryToken,
      @QueryParam("filter") final String timesheetsFilter) {
        String token = SH.processHeaderQueryToken(headerToken, queryToken);
        int userId = userTokens.verifyTokenAndReturnUserID(token);

        List<Timesheet> timesheetList;

        if (timesheetsFilter.equals("current")) {
            // Get only latest timesheets.
            // timesheetDao.getCurrent(int year, int week)
            timesheetList = timesheetDao.getAll();
        } else {
            timesheetList = timesheetDao.getAll();
        }

        return SH.corsResponseWithEntity(200, timesheetList);
    }
}
