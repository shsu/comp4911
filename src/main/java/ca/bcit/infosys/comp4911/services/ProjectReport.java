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
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectMatrixReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final Integer projectId){

        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        List<TimesheetRow>                      wpTimesheetRows;
        Project                                 project;
        ReportHelperRow[]                       reportHelperRows = new ReportHelperRow[20];
        WorkPackageStatusReport                 wpsr;
        List<WorkPackageStatusReport>           latestTwentyReports;
        JSONArray                               reportArray = new JSONArray();
        JSONObject                              objectToBeMapped;
        HashMap<PLevel, BigDecimal>             yearPayRateInfo;

        project = projectDao.read(projectId);
        DateTime dateTime = new DateTime(project.getIssueDate().toString());

        yearPayRateInfo = payRateDao.getPayRateHashByYear().get(dateTime.getYear());

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
            reportHelperRows[i] = new ReportHelperRow(workPackageDao, payRateDao.getPayRateHashByYear());
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectBudgetReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("id") final Integer projectId) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);


        JSONArray projectBudgetJSON = new JSONArray();
        JSONObject pBObject = new JSONObject();
        ProjectBudgetReport pbr = new ProjectBudgetReport(workPackageDao, payRateDao.getPayRateHashByYear());
        List<WorkPackageStatusReport> mostRecentReports = wpsrDao.getAllByProject(projectId);
        mostRecentReports = ReportHelperRow.getSingleWPSRPerWP(mostRecentReports);

        pbr.getExpectedBudget().calculateExpectedPLevelTotalsFromWPSRs(mostRecentReports);
        pbr.getCurrentSpending().calculatePersonHours(tsrDao.getAllByProject(projectId));
        pbr.getInitialBudget().calculateInitialBudget(workPackageDao.getAllProjectWPLeafs(projectId));

        pBObject.put("RemainingEstimate", pbr.getExpectedBudget().getpLevels());
        pBObject.put("RemainingEstimateInDollars", pbr.getExpectedBudget().getLabourDollars());
        pBObject.put("CurrentSpending", pbr.getCurrentSpending().getpLevels());
        pBObject.put("CurrentSpendingInDollars", pbr.getCurrentSpending().getLabourDollars());
        pBObject.put("InitialEstimate", pbr.getInitialBudget().getpLevels());
        pBObject.put("InitialEstimateInDollars", pbr.getInitialBudget().getLabourDollars());


        return SH.responseWithEntity(200, pBObject.toString());
    }

    @GET
    @Path("/work_package/budget/{workpackage_number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkPackageBudgetReport(
            @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
            @QueryParam(SH.TOKEN_STRING) final String queryToken,
            @PathParam("workpackage_number") final String wPNumber) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        JSONObject workPackageObject = new JSONObject();
        ProjectBudgetReport wpBudget = new ProjectBudgetReport(workPackageDao, payRateDao.getPayRateHashByYear());

        if(wPNumber.charAt(6) != '0'){
            wpBudget.getCurrentSpending().calculatePersonHours(tsrDao.getTimesheetRowsByWP(wPNumber));
            workPackageObject.put("workPackagePLevels", wpBudget.getCurrentSpending().getpLevels());
            workPackageObject.put("workPackageBudgetInDollars", wpBudget.getCurrentSpending().getLabourDollars());
        }
        else {
            List<String> wPChildrenString = workPackageDao.getWPChildren(wPNumber);
            List<TimesheetRow> childrensRows = tsrDao.getTimesheetRowsByMultipleWPNumber(wPChildrenString);
            List<WorkPackageStatusReport> childrenWPSRs = wpsrDao.getAllByMultipleWPNumber(wPChildrenString);
            List<WorkPackage> childrenWP = workPackageDao.getWPsByWorkPackageNumbers(wPChildrenString);
            wpBudget.getCurrentSpending().calculatePersonHours(childrensRows);
            workPackageObject.put("CurrentSpending",
                    wpBudget.getCurrentSpending().getpLevels());
            workPackageObject.put("CurrentSpendingInDollars",
                    wpBudget.getCurrentSpending().getLabourDollars());
            wpBudget.getExpectedBudget().
                    calculateExpectedPLevelTotalsFromWPSRs(ReportHelperRow.getSingleWPSRPerWP(childrenWPSRs));
            workPackageObject.put("RemainingEstimate",
                    wpBudget.getExpectedBudget().getpLevels());
            workPackageObject.put("RemainingEstimateInDollars",
                    wpBudget.getExpectedBudget().getLabourDollars());
            wpBudget.getInitialBudget().calculateInitialBudget(childrenWP);
            workPackageObject.put("InitialEstimate",
                    wpBudget.getInitialBudget().getpLevels());
            workPackageObject.put("InitialEstimateInDollars",
                    wpBudget.getInitialBudget().getLabourDollars());

        }

        return SH.responseWithEntity(200, workPackageObject.toString());
    }

}
