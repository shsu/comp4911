package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.helper.SH;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.BaseEncoding;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Singleton
public class UserTokens {

    private Map<String, Integer> tokensForAuthenticatedUserID;

    @PostConstruct
    public void init()  {
        tokensForAuthenticatedUserID = Maps.newHashMap();
    }

    public String generateToken(final int userID) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        tokensForAuthenticatedUserID.put(token, userID);

        return token;
    }

    public boolean clearToken(final String headerToken, final String queryToken) {
        return tokensForAuthenticatedUserID.remove(processHeaderQueryToken(headerToken,queryToken)) != null;
    }

    public int verifyTokenAndReturnUserID(final String headerToken, final String queryToken) throws WebApplicationException {
        Integer userID = tokensForAuthenticatedUserID.get(processHeaderQueryToken(headerToken,queryToken));

        if (userID == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        return userID;
    }

    private String processHeaderQueryToken(final String headerToken, final String queryToken) {
        if (!Strings.isNullOrEmpty(headerToken)) {
            String decodedToken = new String(
              BaseEncoding.base64().decode(headerToken.substring("Basic ".length())), Charsets.UTF_8);
            return decodedToken.substring(0, decodedToken.length() - 1);
        } else if (!Strings.isNullOrEmpty(queryToken)) {
            return queryToken;
        }

        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }
}
