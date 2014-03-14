package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.TimesheetRowDao;
import ca.bcit.infosys.comp4911.access.WorkPackageStatusReportDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.*;
import ca.bcit.infosys.comp4911.helper.ReportHelper;
import ca.bcit.infosys.comp4911.helper.ReportHelperRow;
import ca.bcit.infosys.comp4911.helper.SH;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Dynamically creates report and returns it.
 */
@Path("/reports")
public class ProjectReport {

    public static final int CONVERT_TO_TENTHS_OF_AN_HOUR = 10;

    @EJB
    private UserTokens userTokens;

    @EJB
    private WorkPackageStatusReport workPackageStatusReport;

    @EJB
    private WorkPackageStatusReportDao wpsrDao;

    @EJB
    private ProjectDao projectDao;

    @EJB
    private TimesheetRowDao tsrDao;

    private List<WorkPackageStatusReport> latestTwentyReports;

    private WorkPackageStatusReport wpsr;

    private List<TimesheetRow> wpTimesheetRows;

    private Project project;

    private ReportHelper reportHelper;

    private ReportHelperRow reportHelperRow;

    private Set<Effort> effortSet;

    @GET
    @Path("{id}")
    public Response getProjectReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final Integer projectId){
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        // No need for actual Entity because it is not actually stored in DB
        JSONObject report = new JSONObject();
        project = projectDao.read(projectId);
        reportHelper = new ReportHelper(project.getProjectNumber(), project.getProjectName());
        //latestTwentyReports = wpsrDao.getLatestByProject(projectId);
        Iterator<WorkPackageStatusReport> wpsrIterator = latestTwentyReports.iterator();
        int i = 0;
        reportHelperRow = new ReportHelperRow();
        while(wpsrIterator.hasNext()){
            wpsr = wpsrIterator.next();
            reportHelperRow.setWpNumber(wpsr.getWorkPackageNumber());
            wpTimesheetRows = tsrDao.getAllByWP(wpsr.getWorkPackageNumber());
            calculatePLevels(reportHelperRow, wpTimesheetRows);

        }


        return SH.responseWithEntity(200, report);
    }

    private void calculatePLevels(ReportHelperRow reportHelperRow, List<TimesheetRow> wpTimesheetRows){
        double total = 0;

        Iterator<TimesheetRow> tsrIterator = wpTimesheetRows.listIterator();
        while(tsrIterator.hasNext()){
            total += tsrIterator.next().getTotal();
        }

        reportHelperRow.setP1(total/CONVERT_TO_TENTHS_OF_AN_HOUR);
    }
}
