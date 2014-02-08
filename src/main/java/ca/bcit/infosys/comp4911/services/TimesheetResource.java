package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Graeme on 2/8/14.
 */
@Path("/timesheets")
public class TimesheetResource {

    @EJB
    TimesheetDao timesheetDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTimesheets() {
        return Response.ok().entity(timesheetDao.getAll()).header(SH.cors, "*").build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimesheet(@PathParam("id") Integer id)
    {
        Timesheet timesheet = timesheetDao.read(id);
        if(timesheet == null)
        {
            return Response.status(404).build();
        }
        return Response.ok().entity(timesheet).header(SH.cors, "*").build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTimesheet(@PathParam("id") Integer id, Timesheet timesheet)
    {
        Timesheet update = timesheetDao.read(id);
        if(update == null)
        {
            return Response.status(404).build();
        }
        timesheetDao.update(timesheet);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTimesheet(@PathParam("id") Integer id)
    {
        Timesheet timesheet = timesheetDao.read(id);
        if(timesheet == null)
        {
            return Response.status(404).build();
        }
        timesheetDao.delete(timesheet);
        return Response.ok().build();
    }


}
