package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.services.SH;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import javax.ejb.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class UserTokens implements Serializable {

    private Map<String, Integer> tokensForAuthenticatedUserID;

    public UserTokens() {
        tokensForAuthenticatedUserID = Maps.newHashMap();
    }

    public String generateToken(final int userID) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        tokensForAuthenticatedUserID.put(token, userID);

        return token;
    }

    public int verifyTokenAndReturnUserID(final String tokenToBeVerified)
      throws WebApplicationException {
        if (Strings.isNullOrEmpty(tokenToBeVerified)) {
            throw new WebApplicationException(
              Response.status(Response.Status.UNAUTHORIZED).header(SH.cors,"*").build());
        }

        Integer userID = tokensForAuthenticatedUserID.get(tokenToBeVerified);

        if (userID == null) {
            throw new WebApplicationException(
              Response.status(Response.Status.UNAUTHORIZED).header(SH.cors,"*").build());
        }

        return userID;
    }


    public boolean clearToken(String tokenToBeCleared) throws WebApplicationException {
        if (Strings.isNullOrEmpty(tokenToBeCleared)) {
            throw new WebApplicationException(
              Response.status(Response.Status.UNAUTHORIZED).header(SH.cors,"*").build());
        }

        return tokensForAuthenticatedUserID.remove(tokenToBeCleared) != null;
    }
}
