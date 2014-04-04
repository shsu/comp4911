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
import java.math.BigDecimal;
import java.util.*;
import org.joda.time.DateTime;

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

    @EJB
    private WorkPackageDao workPackageDao;

    @GET
    @Path("/matrix/{id}")
    public Response getProjectMatrixReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final Integer projectId){

        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        List<TimesheetRow>                      wpTimesheetRows;
        Project project;
        WPReportHelperRow[]                     reportHelperRows = new WPReportHelperRow[20];
        WorkPackageStatusReport                 wpsr;
        List<WorkPackageStatusReport>           latestTwentyReports;
        DateTime                                date;
        JSONArray                               reportArray = new JSONArray();
        JSONObject                              objectToBeMapped;
        HashMap<PLevel, BigDecimal>             yearPayRateInfo;
        project = projectDao.read(projectId);

        date = new DateTime(project.getIssueDate().toString());
        yearPayRateInfo = payRateDao.getPayRateHashByYear(date.getYear());
        objectToBeMapped = new JSONObject();
        objectToBeMapped.put("projectNumber", projectId);
        objectToBeMapped.put("projectName", project.getProjectName());
        objectToBeMapped.put("payRates", yearPayRateInfo);
        reportArray.put(objectToBeMapped);

        latestTwentyReports = wpsrDao.getLatestTwentyByProject(projectId);
        if(latestTwentyReports.size() <= 0){
            return SH.responseWithEntity(200, reportArray.toString());
        }
        Iterator<WorkPackageStatusReport> wpsrIterator = latestTwentyReports.iterator();
        int i = 0;
        while(wpsrIterator.hasNext()) {
            objectToBeMapped = new JSONObject();
            wpsr = wpsrIterator.next();
            String year = wpsr.getReportDate().toString();
            date = new DateTime(year);
            yearPayRateInfo = payRateDao.getPayRateHashByYear(date.getYear());
            wpTimesheetRows = tsrDao.getTimesheetRowsByWP(wpsr.getWorkPackageNumber());
            reportHelperRows[i] = new WPReportHelperRow(wpsr.getWorkPackageNumber());
            reportHelperRows[i].setpLevels(getWPPersonHours(wpTimesheetRows).getpLevels());
            reportHelperRows[i].calculateTotalLabourDollars(yearPayRateInfo);
            objectToBeMapped.put("workPackageNumber", wpsr.getWorkPackageNumber());
            objectToBeMapped.put("workPackageDescription",
                    workPackageDao.read(wpsr.getWorkPackageNumber()).getWorkPackageName());
            objectToBeMapped.put("pLevels", reportHelperRows[i].getpLevels());
            objectToBeMapped.put("labourDollars", reportHelperRows[i].getLabourDollars());
            reportArray.put(objectToBeMapped);
            i++;
        }
        return SH.responseWithEntity(200, reportArray.toString());
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
//        pbr.getExpectedBudget().calculateProjectExpectedPLevelTotals(oneStatusReportPerWP);
//
//    }

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
}
