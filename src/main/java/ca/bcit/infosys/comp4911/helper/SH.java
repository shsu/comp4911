package ca.bcit.infosys.comp4911.helper;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import org.joda.time.DateTime;

/**
 * Services Helper
 */
public class SH {
    public static final String AUTHORIZATION_STRING = "Authorization";
    public static final String TOKEN_STRING = "token";

    public static javax.ws.rs.core.Response response(Integer code) {
        return javax.ws.rs.core.Response.status(code).build();
    }

    public static javax.ws.rs.core.Response responseWithEntity(int code, Object entity) {
        if (entity == null) {
            response(code);
        }

        return javax.ws.rs.core.Response.status(code).entity(entity).build();
    }

    public static Integer getCurrentWeek() {
        return new DateTime().weekOfWeekyear().get();
    }

    public static Integer getCurrentYear() {
        return new DateTime().getYear();
    }
}
