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
 * Created by Graeme on 2/8/14.
 */
@Path("/work_packages")
public class WorkPackageResource {

    @EJB
    WorkPackageDao workPackageDao;

    @EJB
    WorkPackageStatusReportDao workPackageStatusReportDao;

    @EJB
    UserTokens userTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllWorkPackages(
            @HeaderParam("Authorization") final String token)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        return Response.ok().entity(workPackageDao.getAll()).header(SH.cors, "*")
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveWorkPackage(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(id);
        if(workPackage == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }
        return Response.ok().entity(workPackage).header(SH.cors, "*")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayRate(
            @HeaderParam("Authorization") final String token,
            WorkPackage workPackage)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        workPackageDao.create(workPackage);
        return Response.status(201).header(SH.cors, "*")
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackage(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id,
            WorkPackage workPackage)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackageUpdate = workPackageDao.read(id);
        if(workPackageUpdate == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }

        workPackageDao.update(workPackage);
        return Response.ok().header(SH.cors, "*")
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteWorkPackage(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(id);
        if(workPackage == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }

        workPackageDao.delete(workPackage);
        return Response.status(204).header(SH.cors, "*")
                .build();
    }

    @GET
    @Path("{id}/status_reports")
    public Response retrieveWorkPackageStatusReports(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(id);
        if(workPackage == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }

        return Response.ok().entity(workPackageStatusReportDao.getAll()).header(SH.cors, "*")
                .build();
    }

    @POST
    @Path("{id}/status_reports")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayRate(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer id,
            WorkPackageStatusReport workPackageStatusReport)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(id);
        if(workPackage == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }

        workPackageStatusReportDao.create(workPackageStatusReport);

        return Response.status(201).header(SH.cors, "*")
                .build();
    }

    @PUT
    @Path("{id}/status_reports/{report_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWorkPackageStatusReport(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer workPackageId,
            @PathParam("report_id") Integer reportId,
            WorkPackageStatusReport workPackageStatusReport)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage wpUpdate = workPackageDao.read(workPackageId);
        if(wpUpdate == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }

        WorkPackageStatusReport reportUpdate = workPackageStatusReportDao.read(reportId);
        if(reportUpdate == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }

        workPackageStatusReportDao.update(workPackageStatusReport);
        return Response.ok().header(SH.cors, "*")
                .build();
    }

    @DELETE
    @Path("{id}/status_reports/{report_id}")
    public Response deleteWorkPackageStatusReport(
            @HeaderParam("Authorization") final String token,
            @PathParam("id") Integer workPackageId,
            @PathParam("reportId") Integer reportId)
    {
        int userId = userTokens.verifyTokenAndReturnUserID((token));

        WorkPackage workPackage = workPackageDao.read(workPackageId);
        if(workPackage == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }

        WorkPackageStatusReport workPackageStatusReport = workPackageStatusReportDao.read(reportId);
        if(workPackageStatusReport == null)
        {
            return Response.status(404).header(SH.cors, "*")
                    .build();
        }

        workPackageStatusReportDao.delete(workPackageStatusReport);
        return Response.status(204).header(SH.cors, "*")
                .build();
    }

}
