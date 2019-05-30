package services;

import dao.blueprint.IJsonWebTokenDAO;
import models.AuthUser;
import models.JsonWebToken;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class JsonWebTokenService {

    @Inject
    IJsonWebTokenDAO jsonWebTokenDAO;

    public JsonWebToken addJsonWebToken(String tokenString, long validUntil, AuthUser authUser) {
        return jsonWebTokenDAO.addJsonWebToken(new JsonWebToken(tokenString, validUntil, authUser));
    }

    public AuthUser getAuthUserByValidJsonWebTokenString(String tokenString)
    {
        JsonWebToken validJsonWebToken = jsonWebTokenDAO.findValidJsonWebTokenByTokenString(tokenString);
        return validJsonWebToken.getAuthUser();
    }
}
