package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.access.WorkPackageStatusReportDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.domain.WorkPackageStatusReport;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Graeme on 2/11/14.
 */
@Path("/work_packages/{id}/status_reports")
public class StatusReportResource {

    @EJB
    private UserTokens userTokens;

    @EJB
    private WorkPackageDao workPackageDao;

    @EJB
    private WorkPackageStatusReportDao workPackageStatusReportDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveWorkPackageResponseReports(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") final String id) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage check = workPackageDao.read(id);
        if (check == null) {
            return SH.response(404);
        }

        return SH.responseWithEntity(200, workPackageStatusReportDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWorkPackageResponseReport(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") String id,
      final WorkPackageStatusReport workPackageStatusReport) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage check = workPackageDao.read(id);
        if (check == null) {
            return SH.response(404);
        }

        workPackageStatusReportDao.create(workPackageStatusReport);
        return SH.response(201);
    }

    @GET
    @Path("{report_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveWorkPackageStatusReport(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") String workPackageId,
      @PathParam("report_id") Integer reportId) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage check = workPackageDao.read(workPackageId);
        if (check == null) {
            return SH.response(404);
        }

        WorkPackageStatusReport workPackageStatusReport = workPackageStatusReportDao.read(reportId);
        if (workPackageStatusReport == null) {
            return SH.response(404);
        }

        return SH.responseWithEntity(200, workPackageStatusReport);
    }

    @PUT
    @Path("{report_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackageResponseReport(
      @HeaderParam(SH.AUTHORIZATION_STRING) final String headerToken,
      @QueryParam(SH.TOKEN_STRING) final String queryToken,
      @PathParam("id") String workPackageId,
      @PathParam("report_id") Integer reportId,
      final WorkPackageStatusReport workPackageStatusReport) {
        int userId = userTokens.verifyTokenAndReturnUserID(headerToken, queryToken);

        WorkPackage check = workPackageDao.read(workPackageId);
        if (check == null) {
            return SH.response(404);
        }

        WorkPackageStatusReport checkReport = workPackageStatusReportDao.read(reportId);
        if (checkReport == null) {
            return SH.response(404);
        }

        workPackageStatusReportDao.update(workPackageStatusReport);
        return SH.response(200);
    }
}

