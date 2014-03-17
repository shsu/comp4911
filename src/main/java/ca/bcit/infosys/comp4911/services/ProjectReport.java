package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.*;
import ca.bcit.infosys.comp4911.helper.ReportHelper;
import ca.bcit.infosys.comp4911.helper.ReportHelperRow;
import ca.bcit.infosys.comp4911.helper.SH;
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
    private WorkPackageStatusReport workPackageStatusReport;

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

        List<Timesheet> wpTimesheets;
        Project project;
        ReportHelper reportHelper;
        ReportHelperRow[] reportHelperRows = new ReportHelperRow[20];
        WorkPackageStatusReport wpsr;
        List<WorkPackageStatusReport> latestTwentyReports;

        // No need for actual Entity because it is not actually stored in DB
        JSONObject report = new JSONObject();
        project = projectDao.read(projectId);
        reportHelper = new ReportHelper(project.getProjectNumber(), project.getProjectName());

        latestTwentyReports = wpsrDao.getLatestByProject(projectId);
        Iterator<WorkPackageStatusReport> wpsrIterator = latestTwentyReports.iterator();
        int i = 0;
        while(wpsrIterator.hasNext()){
            wpsr = wpsrIterator.next();
            wpTimesheets = tsDao.getTimesheetsByWP(wpsr.getWorkPackageNumber());
            reportHelperRows[i] = getWPPersonHours(wpTimesheets, wpsr.getWorkPackageNumber());
            calculateTotalLabourDollars(reportHelperRows[i], wpsr.getYear());
            i++;
        }

        reportHelper.setRhRows(reportHelperRows);
        reportHelper.setProjectName(project.getProjectName());
        reportHelper.setProjectNumber(project.getProjectNumber());
        report.append("report", reportHelper);

        return SH.responseWithEntity(200, report);
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
     * This method uses the userId, and the timesheet date to find the user PLevel for a given timesheet.
     * @param userId, year, weekNumber
     * @return
     */
    private PLevel getUserPLevel (int userId, int year, int weekNumber) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, 52);
        Date date = cal.getTime();
        UserPayRateHistory userPayRateHistory = userPayRateHistoryDao.getByUserIdAndTimesheetDate(userId, date);

        return userPayRateHistory.getpLevel();
    }

    /**
     * Gets the amount of PLevel days per a list of Timesheets. This iterates through all of the Timesheets associated
     * with a specific Work Package. It then updates the amount of PLevels used per work package.
     * @param timesheetList, wpNumber
     * @return - the finished reportHelperRow
     */
    private ReportHelperRow getWPPersonHours(List<Timesheet> timesheetList, String wpNumber){
        ReportHelperRow reportHelperRow = new ReportHelperRow();
        Timesheet currentTimesheet;
        List<TimesheetRow> timesheetRowList;
        PLevel plevel;
        Iterator<Timesheet> timesheetIterator = timesheetList.iterator();

        while(timesheetIterator.hasNext())
        {
            currentTimesheet = timesheetIterator.next();

            plevel = getUserPLevel(currentTimesheet.getUserId(),
                    currentTimesheet.getYear(), currentTimesheet.getWeekNumber());
            timesheetRowList = currentTimesheet.getTimesheetRows();
            reportHelperRow = incrementPLevelHours(plevel, timesheetRowList, wpNumber, reportHelperRow);
        }

        return reportHelperRow;
    }

    /**
     * This method is used to update the PLevels. A Timesheet's rows are passed as parameter. The method iterates through
     * the timesheet rows. If the work package number for Timesheet Row matches the given work package number then
     * the report row's PLevel hours are updated.
     * @param plevel
     * @param timesheetRowList
     * @param wpNumber
     * @param reportHelperRow
     * @return
     */
    private ReportHelperRow incrementPLevelHours(PLevel plevel, List<TimesheetRow> timesheetRowList, String wpNumber,
                                                 ReportHelperRow reportHelperRow){
        Iterator<TimesheetRow> timesheetRowIterator = timesheetRowList.iterator();
        TimesheetRow tsr;
        while(timesheetRowIterator.hasNext()){
            tsr = timesheetRowIterator.next();
            if(!(tsr.getWorkPackageNumber().equalsIgnoreCase(wpNumber)))
            {
                continue;
            }
            switch(plevel){
                case P1:
                    reportHelperRow.incrementP1(tsr.calculateTotal());
                    break;
                case P2:
                    reportHelperRow.incrementP2(tsr.calculateTotal());
                    break;
                case P3:
                    reportHelperRow.incrementP3(tsr.calculateTotal());
                    break;
                case P4:
                    reportHelperRow.incrementP4(tsr.calculateTotal());
                    break;
                case P5:
                    reportHelperRow.incrementP5(tsr.calculateTotal());
                    break;
                case SS:
                    reportHelperRow.incrementSS(tsr.calculateTotal());
                    break;
                case DS:
                    reportHelperRow.incrementDS(tsr.calculateTotal());
                    break;
            }

        }

        return reportHelperRow;
    }
}
