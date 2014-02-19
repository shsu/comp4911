package ca.bcit.infosys.comp4911.helper;

import javax.persistence.Entity;
import org.joda.time.DateTime;

/**
 * Services Helper
 */
public class SH {
    public static final String cors = "Access-Control-Allow-Origin";
    public static final String auth = "Authorization";

    public static javax.ws.rs.core.Response Response(Integer code) {
        return javax.ws.rs.core.Response.status(code).header(SH.cors, "*")
                .build();
    }

    public static javax.ws.rs.core.Response Response(Integer code, Object entity) {
        return javax.ws.rs.core.Response.status(code).entity(entity).header(SH.cors, "*")
                .build();
    }

    public static Integer getCurrentWeek() {
        DateTime dt = new DateTime();
        return dt.weekOfWeekyear().get();
    }

    public static Integer getCurrentYear(){
        DateTime dt = new DateTime();
        return dt.getYear();
    }
}
