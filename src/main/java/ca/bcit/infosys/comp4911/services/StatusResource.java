package ca.bcit.infosys.comp4911.services;

import ca.bcit.infosys.comp4911.helper.SH;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class StatusResource {

    @GET
    @Produces("text/html")
    public String test() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<link href=\"//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css\" rel=\"stylesheet\">");
        stringBuffer.append("<div class=\"container\"><br />");
        stringBuffer.append("<div class=\"alert alert-success\"><h3>All Systems Operational</h3></div>");
        stringBuffer.append("</div>");
        return stringBuffer.toString();
    }

    @GET
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response jsonTest(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test","success");

        return SH.Response(200,jsonObject.toString());
    }

}
