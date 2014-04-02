package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.*;
import ca.bcit.infosys.comp4911.helper.*;
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
    @Path("/matrix/{id}")
    public Response getProjectMatrixReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final Integer projectId){
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        List<TimesheetRow> wpTimesheetRows;
        Project project;
        ReportHelper reportHelper;
        ReportHelperRow[] reportHelperRows = new WPReportHelperRow[20];
        WorkPackageStatusReport wpsr;
        List<WorkPackageStatusReport> latestTwentyReports;

        // No need for actual Entity because it is not actually stored in DB
        JSONObject report = new JSONObject();
        JSONArray reportArray = new JSONArray();
        project = projectDao.read(projectId);
        reportHelper = new ReportHelper(project.getProjectNumber(), project.getProjectName());

        latestTwentyReports = wpsrDao.getLatestTwentyByProject(projectId);
        Iterator<WorkPackageStatusReport> wpsrIterator = latestTwentyReports.iterator();
        int i = 0;
        while(wpsrIterator.hasNext()) {
            wpsr = wpsrIterator.next();
            wpTimesheetRows = tsrDao.getTimesheetRowsByWP(wpsr.getWorkPackageNumber());
            reportHelperRows[i] = getWPPersonHours(wpTimesheetRows);
            reportHelperRows[i].setWpNumber(wpsr.getWorkPackageNumber());
            calculateTotalLabourDollars(reportHelperRows[i], wpsr.getReportDate().getYear());
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
     * Endpoint used to return JSON object representing the PCBAC report
     * @param headerToken
     * @param queryToken
     * @param projectId
     * @return
     */
//    @GET
//    @Path("/budget/{id}")
//    public Response getProjectBudgetReport(
//            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
//            @QueryParam(SH.TOKEN_STRING) final String queryToken,
//            @PathParam("id") final Integer projectId) {
//        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);
//
//        ArrayList<WorkPackageStatusReport> mostRecentReports = (ArrayList)wpsrDao.getAllByProject(projectId);
//        ArrayList<Effort> effort;
//        ReportHelperRow newHelper = new ReportHelperRow();
//        ProjectBudgetReport pbr = new ProjectBudgetReport();
//        ArrayList<WorkPackageStatusReport> oneStatusReportPerWP = getSingleWPSRPerWP(mostRecentReports);
//        calculateProjectExpectedPLevelTotals(oneStatusReportPerWP, pbr.getExpectedBudget());
//
//
//
//
//
//    }

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
        totalLabourDollars += yearPayRate.get(0).getRate().doubleValue()*reportHelperRow.getpLevels().get(PLevel.DS);
        totalLabourDollars += yearPayRate.get(1).getRate().doubleValue()*reportHelperRow.getpLevels().get(PLevel.P1);
        totalLabourDollars += yearPayRate.get(2).getRate().doubleValue()*reportHelperRow.getpLevels().get(PLevel.P2);
        totalLabourDollars += yearPayRate.get(3).getRate().doubleValue()*reportHelperRow.getpLevels().get(PLevel.P3);
        totalLabourDollars += yearPayRate.get(4).getRate().doubleValue()*reportHelperRow.getpLevels().get(PLevel.P4);
        totalLabourDollars += yearPayRate.get(5).getRate().doubleValue()*reportHelperRow.getpLevels().get(PLevel.P5);
        totalLabourDollars += yearPayRate.get(6).getRate().doubleValue()*reportHelperRow.getpLevels().get(PLevel.SS);
        reportHelperRow.setLabourDollars(totalLabourDollars);
    }

    /**
     * Gets the amount of PLevel days per a list of Timesheets. This iterates through all of the Timesheets associated
     * with a specific Work Package. It then updates the amount of PLevels used per work package.
     * @param timesheetRowList, wpNumber
     * @return - the finished reportHelperRow
     */
    private ReportHelperRow getWPPersonHours(List<TimesheetRow> timesheetRowList){
        ReportHelperRow reportHelperRow = new ReportHelperRow();
        Iterator<TimesheetRow> timesheetRowIterator = timesheetRowList.iterator();
        TimesheetRow tsr;
        while(timesheetRowIterator.hasNext())
        {
            tsr = timesheetRowIterator.next();
            reportHelperRow.increasePLevel(tsr.getpLevel(), tsr.calculateTotal());
        }

        return reportHelperRow;
    }

    private ArrayList<WorkPackageStatusReport> getSingleWPSRPerWP(List<WorkPackageStatusReport> allWPSR) {
        /**
         * HashMap is used to check for double WPSRs. Check for double in order to only take the most
         * recent WPSR.
         */
        HashMap<String, Boolean> singleWPSRPerWP = new HashMap<String,
                Boolean>();
        ArrayList<WorkPackageStatusReport> mostRecentWPSR = new ArrayList<WorkPackageStatusReport>();
        WorkPackageStatusReport currentWPSR;
        Iterator<WorkPackageStatusReport> wpsrIterator = allWPSR.listIterator();
        while(wpsrIterator.hasNext()){
            currentWPSR = wpsrIterator.next();
            if(singleWPSRPerWP.containsKey(currentWPSR.getWorkPackageNumber())){
                continue;
            }
            else {
                singleWPSRPerWP.put(currentWPSR.getWorkPackageNumber(), true);
                mostRecentWPSR.add(currentWPSR);

            }

        }
        return mostRecentWPSR;
    }

    private void calculateProjectExpectedPLevelTotals(ArrayList<WorkPackageStatusReport> oneStatusReportPerWP,
                                                 ReportHelperRow projectBudget){
        ReportHelperRow newHelper = new ReportHelperRow();
        int length = oneStatusReportPerWP.size();
        ArrayList<Effort> effort = new ArrayList<Effort>();
        for(int i = 0; i < length; i++){
            effort = (ArrayList)oneStatusReportPerWP.get(i).getEstimatedWorkRemainingInPD();
            for(int j = 0; j < effort.size(); j++){
                projectBudget.increasePLevel(effort.get(j).getpLevel(), effort.get(j).getPersonDays());
            }
        }
    }
    
}
