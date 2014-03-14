package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.WorkPackageStatusReportDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.Effort;
import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.WorkPackageStatusReport;
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

    @EJB
    private UserTokens userTokens;

    @EJB
    private WorkPackageStatusReport workPackageStatusReport;

    @EJB
    private WorkPackageStatusReportDao wpsrDao;

    @EJB
    private ProjectDao projectDao;

    private List<WorkPackageStatusReport> latestTwentyReports;

    private WorkPackageStatusReport wpsr;

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

        }


        return SH.responseWithEntity(200, report);
    }
}
