package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.*;
import ca.bcit.infosys.comp4911.helper.*;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
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
        JSONArray                               reportArray = new JSONArray();
        JSONObject                              objectToBeMapped;
        HashMap<PLevel, BigDecimal>             yearPayRateInfo;

        project = projectDao.read(projectId);

        yearPayRateInfo = getPLevelBigDecimalHashForYear(project.getIssueDate());

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
            wpTimesheetRows = tsrDao.getTimesheetRowsByWP(wpsr.getWorkPackageNumber());
            reportHelperRows[i] = new WPReportHelperRow(wpsr.getWorkPackageNumber());
            reportHelperRows[i].calculatePersonHours(wpTimesheetRows);
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
    @GET
    @Path("/budget/{id}")
    public Response getProjectBudgetReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final Integer projectId) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);


        JSONArray projectBudgetJSON = new JSONArray();
        JSONObject pBObject = new JSONObject();
        ProjectBudgetReport pbr = new ProjectBudgetReport();
        List<WorkPackageStatusReport> mostRecentReports = wpsrDao.getAllByProject(projectId);
        mostRecentReports = getSingleWPSRPerWP(mostRecentReports);

        pbr.getExpectedBudget().calculateExpectedPLevelTotalsFromWPSRs(mostRecentReports);
        pbr.getCurrentSpending().calculatePersonHours(tsrDao.getAllByProject(projectId));
        pbr.getInitialBudget().calculateInitialBudget(workPackageDao.getAllByProject(projectId));

        pBObject.put("ExpectedBudget", pbr.getExpectedBudget().getpLevels());
        pBObject.put("ExpectedBusgetInDollars", pbr.getExpectedBudget().getLabourDollars());
        pBObject.put("CurrentSpending", pbr.getCurrentSpending());
        pBObject.put("CurrentSpendingInDollar", pbr.getCurrentSpending().getLabourDollars());
        pBObject.put("InitialBudget", pbr.getInitialBudget());
        pBObject.put("InitialBudgetInDollars", pbr.getInitialBudget().getLabourDollars());


        return SH.responseWithEntity(200, pBObject.toString());
    }

    @GET
    @Path("/work_package/budget/{workpackage_number}")
    public Response getWorkPackageBudgetReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("workpackage_number") final String wPNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        JSONObject workPackageObject = new JSONObject();
        ReportHelperRow wpBudget = new ReportHelperRow();

        if(wPNumber.charAt(6) != '0'){
            wpBudget.calculatePersonHours(tsrDao.getTimesheetRowsByWP(wPNumber));
            workPackageObject.put("workPackagePLevels", wpBudget.getpLevels());
            workPackageObject.put("workPackageBudgetInDollars", wpBudget.getLabourDollars());
        }
        else {
            List<String> wPChildren = workPackageDao.getWPChildren(wPNumber);
            Iterator<String> childrenIterator = wPChildren.listIterator();
            List<TimesheetRow> childrensRows = new ArrayList<TimesheetRow>();
            String childWPNumber;
            while(childrenIterator.hasNext()){
                childWPNumber = childrenIterator.next();
                childrensRows.addAll(tsrDao.getTimesheetRowsByWP(childWPNumber));
            }
            wpBudget.calculatePersonHours(childrensRows);
            workPackageObject.put("workPackagePLevels", wpBudget.getpLevels());
            workPackageObject.put("workPackageBudgetInDollars", wpBudget.getLabourDollars());
        }

        return SH.responseWithEntity(200, wpBudget);
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

    public HashMap<PLevel, BigDecimal> getPLevelBigDecimalHashForYear(Date date){
        DateTime dateTime = new DateTime(date.toString());
        return payRateDao.getPayRateHashByYear(dateTime.getYear());
    }

}
