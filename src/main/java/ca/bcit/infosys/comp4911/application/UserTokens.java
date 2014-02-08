package ca.bcit.infosys.comp4911.application;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class UserTokens implements Serializable {

    private Map<String, Integer> tokensForAuthenticatedUserID;

    public UserTokens() {
        tokensForAuthenticatedUserID = new HashMap<String, Integer>();
    }

    public boolean clearToken(String tokenToBeClearerd) {
        Integer userID = tokensForAuthenticatedUserID.remove(tokenToBeClearerd);

        if (userID != null) {
            return true;
        }

        return false;
    }

    public String generateToken(final int userID) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        tokensForAuthenticatedUserID.put(token, userID);

        return token;
    }

    public int verifyTokenAndReturnUserID(final String tokenToBeVerified)
      throws WebApplicationException {
        if (tokenToBeVerified != null) {
            Integer userID = tokensForAuthenticatedUserID.get(tokenToBeVerified);

            if (userID != null) {
                return userID;
            }
        }

        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }
}
