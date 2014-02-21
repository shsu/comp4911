package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.access.WorkPackageStatusReportDao;
import ca.bcit.infosys.comp4911.application.UserTokens;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.domain.WorkPackageStatusReport;
import ca.bcit.infosys.comp4911.helper.SH;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Graeme on 2/11/14.
 */
@Path("/work_packages/{id}/status_reports")
public class StatusReportResource {

    @EJB
    UserTokens userTokens;

    @EJB
    WorkPackageDao workPackageDao;

    @EJB
    WorkPackageStatusReportDao workPackageStatusReportDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveWorkPackageResponseReports(
            @HeaderParam(SH.auth) final String token,
            @PathParam("id") final Integer id) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage check = workPackageDao.read(id);
        if (check == null) {
            return SH.corsResponse(404);
        }

        return SH.corsResponseWithEntity(200, workPackageStatusReportDao.getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWorkPackageResponseReport(
            @HeaderParam(SH.auth) final String token,
            @PathParam("id") Integer id,
            WorkPackageStatusReport workPackageStatusReport) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage check = workPackageDao.read(id);
        if (check == null) {
            return SH.corsResponse(404);
        }

        workPackageStatusReportDao.create(workPackageStatusReport);
        return SH.corsResponse(201);
    }

    @GET
    @Path("{report_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveWorkPackageStatusReport(
            @HeaderParam(SH.auth) final String token,
            @PathParam("id") Integer workPackageId,
            @PathParam("report_id") Integer reportId) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage check = workPackageDao.read(workPackageId);
        if (check == null) {
            return SH.corsResponse(404);
        }

        WorkPackageStatusReport workPackageStatusReport = workPackageStatusReportDao.read(reportId);
        if (workPackageStatusReport == null) {
            return SH.corsResponse(404);
        }

        return SH.corsResponseWithEntity(200, workPackageStatusReport);
    }

    @PUT
    @Path("{report_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackageResponseReport(
            @HeaderParam(SH.auth) final String token,
            @PathParam("id") Integer workPackageId,
            @PathParam("report_id") Integer reportId,
            WorkPackageStatusReport workPackageStatusReport) {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage check = workPackageDao.read(workPackageId);
        if (check == null) {
            return SH.corsResponse(404);
        }

        WorkPackageStatusReport checkReport = workPackageStatusReportDao.read(reportId);
        if (checkReport == null) {
            return SH.corsResponse(404);
        }

        workPackageStatusReportDao.update(workPackageStatusReport);
        return SH.corsResponse(200);
    }
}

