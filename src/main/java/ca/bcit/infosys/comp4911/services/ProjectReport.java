package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.*;
import ca.bcit.infosys.comp4911.helper.ReportHelper;
import ca.bcit.infosys.comp4911.helper.ReportHelperRow;
import ca.bcit.infosys.comp4911.helper.SH;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Dynamically creates report and returns it.
 */
@Path("/reports")
public class ProjectReport {

    @EJB
    private UserTokens userTokens;

    @EJB
    private WorkPackageStatusReportDao wpsrDao;

    @EJB
    private ProjectDao projectDao;

    @EJB
    private TimesheetRowDao tsrDao;

    @EJB
    private TimesheetDao tsDao;

    @EJB
    private UserDao userDao;

    @EJB
    private UserPayRateHistoryDao userPayRateHistoryDao;

    @EJB
    private PayRateDao payRateDao;

    @GET
    @Path("{id}")
    public Response getProjectReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final Integer projectId){
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        List<TimesheetRow> wpTimesheetRows;
        Project project;
        ReportHelper reportHelper;
        ReportHelperRow[] reportHelperRows = new ReportHelperRow[20];
        WorkPackageStatusReport wpsr;
        List<WorkPackageStatusReport> latestTwentyReports;

        // No need for actual Entity because it is not actually stored in DB
        JSONObject report = new JSONObject();
        JSONArray reportArray = new JSONArray();
        project = projectDao.read(projectId);
        reportHelper = new ReportHelper(project.getProjectNumber(), project.getProjectName());

        latestTwentyReports = wpsrDao.getLatestByProject(projectId);
        Iterator<WorkPackageStatusReport> wpsrIterator = latestTwentyReports.iterator();
        int i = 0;
        while(wpsrIterator.hasNext()) {
            wpsr = wpsrIterator.next();
            wpTimesheetRows = tsrDao.getTimesheetRowsByWP(wpsr.getWorkPackageNumber());
            reportHelperRows[i] = getWPPersonHours(wpTimesheetRows, wpsr.getWorkPackageNumber());
            calculateTotalLabourDollars(reportHelperRows[i], wpsr.getYear());
            String key = "WP" + i;
            report.append(key, reportHelperRows[i].toString());
            i++;
        }

        report.append("ProjectName", reportHelper.getProjectName());
        report.append("ProjectNumber", reportHelper.getProjectNumber());
        report.append("WorkPackages", reportArray);

        return SH.responseWithEntity(200, report.toString());
    }

    /**
     * This method is used to access the current PayRate for the work packages year. Once the PayRate for the given
     * year has been found, the rate for each Pay Level is used to calculate the total Labour dollars. It is then
     * stored in the ReportHelperRow.
     * @param reportHelperRow
     * @param year
     */
    public void calculateTotalLabourDollars(ReportHelperRow reportHelperRow, int year){
        List<PayRate> yearPayRate = payRateDao.getPayRateByYear(year);
        double totalLabourDollars = 0;
        totalLabourDollars += yearPayRate.get(0).getRate().doubleValue()*reportHelperRow.getDS();
        totalLabourDollars += yearPayRate.get(1).getRate().doubleValue()*reportHelperRow.getP1();
        totalLabourDollars += yearPayRate.get(2).getRate().doubleValue()*reportHelperRow.getP2();
        totalLabourDollars += yearPayRate.get(3).getRate().doubleValue()*reportHelperRow.getP3();
        totalLabourDollars += yearPayRate.get(4).getRate().doubleValue()*reportHelperRow.getP4();
        totalLabourDollars += yearPayRate.get(5).getRate().doubleValue()*reportHelperRow.getP5();
        totalLabourDollars += yearPayRate.get(6).getRate().doubleValue()*reportHelperRow.getSS();
        reportHelperRow.setLabourDollars(totalLabourDollars);
    }

    /**
     * Gets the amount of PLevel days per a list of Timesheets. This iterates through all of the Timesheets associated
     * with a specific Work Package. It then updates the amount of PLevels used per work package.
     * @param timesheetRowList, wpNumber
     * @return - the finished reportHelperRow
     */
    private ReportHelperRow getWPPersonHours(List<TimesheetRow> timesheetRowList, String wpNumber){
        ReportHelperRow reportHelperRow = new ReportHelperRow(wpNumber);
        Iterator<TimesheetRow> timesheetRowIterator = timesheetRowList.iterator();
        TimesheetRow tsr;
        while(timesheetRowIterator.hasNext())
        {
            tsr = timesheetRowIterator.next();
            addTotalHoursPerRow(reportHelperRow, tsr.getpLevel(), tsr.calculateTotal());
        }

        return reportHelperRow;
    }
    
    private void addTotalHoursPerRow(ReportHelperRow reportHelperRow, PLevel plevel, int hours) {
        
        switch(plevel){
            case P1:
                reportHelperRow.incrementP1(hours);
                break;
            case P2:
                reportHelperRow.incrementP2(hours);
                break;
            case P3:
                reportHelperRow.incrementP3(hours);
                break;
            case P4:
                reportHelperRow.incrementP4(hours);
                break;
            case P5:
                reportHelperRow.incrementP5(hours);
                break;
            case SS:
                reportHelperRow.incrementSS(hours);
                break;
            case DS:
                reportHelperRow.incrementDS(hours);
                break;
        }
    }
    
}
