package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.helper.SH;
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

    public int verifyTokenAndReturnUserID(final String authHeader)
      throws WebApplicationException {
        if (Strings.isNullOrEmpty(authHeader)) {
            throw new WebApplicationException(
              Response.status(Response.Status.UNAUTHORIZED).header(SH.cors, "*").build());
        }

        String decodedToken = new String(
          BaseEncoding.base64().decode(authHeader.substring("Basic ".length())), UTF_8);

        Integer userID = tokensForAuthenticatedUserID.get(decodedToken);

        if (userID == null) {
            throw new WebApplicationException(
              Response.status(Response.Status.UNAUTHORIZED).header(SH.cors, "*").build());
        }

        return userID;
    }


    public boolean clearToken(String tokenToBeCleared) throws WebApplicationException {
        if (Strings.isNullOrEmpty(tokenToBeCleared)) {
            throw new WebApplicationException(
              Response.status(Response.Status.UNAUTHORIZED).header(SH.cors, "*").build());
        }

        return tokensForAuthenticatedUserID.remove(tokenToBeCleared) != null;
    }
}
