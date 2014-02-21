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

    public static javax.ws.rs.core.Response corsResponse(Integer code) {
        return javax.ws.rs.core.Response.status(code).header("Access-Control-Allow-Origin", "*")
          .build();
    }

    public static javax.ws.rs.core.Response corsResponseWithEntity(Integer code, Object entity) {
        return javax.ws.rs.core.Response.status(code).entity(entity).header("Access-Control-Allow-Origin", "*")
          .build();
    }

    public static String processHeaderQueryToken(final String headerToken, final String queryToken) {
        String token = null;

        if (!Strings.isNullOrEmpty(headerToken)) {
            String decodedToken = new String(
              BaseEncoding.base64().decode(headerToken.substring("Basic ".length())), Charsets.UTF_8);
            token = decodedToken.substring(0, decodedToken.length() - 1);
        } else if (!Strings.isNullOrEmpty(queryToken)) {
            token = queryToken;
        }

        return token;
    }

    public static Integer getCurrentWeek() {
        return new DateTime().weekOfWeekyear().get();
    }

    public static Integer getCurrentYear() {
        return new DateTime().getYear();
    }
}
