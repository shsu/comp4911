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

        List<TimesheetRow>                              wpTimesheetRows;
        Project                                         project;
        ReportHelperRow[]                               reportHelperRows = new ReportHelperRow[20];
        String                                          wpsr;
        List<String>                                    latestTwentyReports;
        JSONArray                                       reportArray = new JSONArray();
        JSONObject                                      objectToBeMapped;
        HashMap<Integer, HashMap<PLevel, BigDecimal>>   yearPayRateInfo;
        HashMap<String, List<TimesheetRow>>             wPNumberTSRListHash;
        HashMap<String, String>                         wPNumberDescriptionHash;

        project = projectDao.read(projectId);
        DateTime dateTime = new DateTime(project.getIssueDate().toString());

        yearPayRateInfo = payRateDao.getPayRateHashByYear();

        objectToBeMapped = new JSONObject();
        objectToBeMapped.put("projectNumber", projectId);
        objectToBeMapped.put("projectName", project.getProjectName());
        objectToBeMapped.put("payRates", yearPayRateInfo.get(dateTime.getYear()));
        reportArray.put(objectToBeMapped);

        latestTwentyReports = wpsrDao.getLatestTwentyByProject(projectId);
        wPNumberTSRListHash = tsrDao.getWPNumberTimesheetRowListHash(latestTwentyReports);
        wPNumberDescriptionHash = workPackageDao.getWPNumberNameHash(latestTwentyReports);

        if(latestTwentyReports.size() <= 0){
            return SH.responseWithEntity(200, reportArray.toString());
        }

        Iterator<String> wpsrIterator = latestTwentyReports.iterator();
        int i = 0;
        while(wpsrIterator.hasNext()) {
            objectToBeMapped = new JSONObject();
            wpsr = wpsrIterator.next();
            if(wPNumberTSRListHash.get(wpsr) == null) { continue; }
            wpTimesheetRows = wPNumberTSRListHash.get(wpsr);
            reportHelperRows[i] = new ReportHelperRow(workPackageDao, yearPayRateInfo);
            reportHelperRows[i].calculatePersonHours(wpTimesheetRows);
            objectToBeMapped.put("workPackageNumber", wpsr);
            objectToBeMapped.put("workPackageDescription",
                    wPNumberDescriptionHash.get(wpsr));
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

        JSONObject                      pBObject = new JSONObject();
        ProjectBudgetReport             pbr = new ProjectBudgetReport(workPackageDao, payRateDao.getPayRateHashByYear());
        List<WorkPackageStatusReport>   mostRecentReports = wpsrDao.getAllByProject(projectId);

        mostRecentReports = ReportHelperRow.getSingleWPSRPerWP(mostRecentReports);

        pbr.getExpectedBudget().calculateExpectedPLevelTotalsFromWPSRs(mostRecentReports,
                workPackageDao.getAllProjectWPLeafs(projectId));
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

        JSONObject          workPackageObject = new JSONObject();
        ProjectBudgetReport wpBudget = new ProjectBudgetReport(workPackageDao, payRateDao.getPayRateHashByYear());
        List<String> wPChildrenString;

        if(wPNumber.charAt(6) != '0'){
            wPChildrenString = new ArrayList<String>();
            wPChildrenString.add(wPNumber);
        }
        else {
            wPChildrenString = workPackageDao.getWPChildren(wPNumber);
        }
            List<TimesheetRow> childrensRows = tsrDao.getTimesheetRowsByMultipleWPNumber(wPChildrenString);
            List<WorkPackageStatusReport> childrenWPSRs = wpsrDao.getAllByMultipleWPNumber(wPChildrenString);
            List<WorkPackage> childrenWP = workPackageDao.getWPsByWorkPackageNumbers(wPChildrenString);

            wpBudget.getCurrentSpending().calculatePersonHours(childrensRows);
            workPackageObject.put("CurrentSpending",
                    wpBudget.getCurrentSpending().getpLevels());
            workPackageObject.put("CurrentSpendingInDollars",
                    wpBudget.getCurrentSpending().getLabourDollars());
            wpBudget.getExpectedBudget().
                    calculateExpectedPLevelTotalsFromWPSRs(ReportHelperRow.getSingleWPSRPerWP(childrenWPSRs),
                            childrenWP);
            workPackageObject.put("RemainingEstimate",
                    wpBudget.getExpectedBudget().getpLevels());
            workPackageObject.put("RemainingEstimateInDollars",
                    wpBudget.getExpectedBudget().getLabourDollars());
            wpBudget.getInitialBudget().calculateInitialBudget(childrenWP);
            workPackageObject.put("InitialEstimate",
                    wpBudget.getInitialBudget().getpLevels());
            workPackageObject.put("InitialEstimateInDollars",
                    wpBudget.getInitialBudget().getLabourDollars());

            return SH.responseWithEntity(200, workPackageObject.toString());
    }

}
