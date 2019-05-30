package services;

import dao.blueprint.IAuthUserDAO;
import models.AuthUser;
import models.AuthUserRole;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.HashMap;
import java.util.List;

@Stateless
public class AuthUserService {

    @Inject
    IAuthUserDAO authUserDAO;

    @Inject
    AuthenticationService authenticationService;

    public AuthUser login(ContainerRequestContext requestContext) {
        HashMap<String, String> credentials = getCredentials(requestContext);
        AuthUser authUser = authUserDAO.findAuthUserByCredentials(credentials.get("username"), credentials.get("password"));
        if (authUser != null)
        {
            String jsonWebToken = authenticationService.createJWT(String.valueOf(authUser.getId()), authUser.getUsername());
            authUser.setToken(jsonWebToken);
            // TODO: Create webtoken in DB
            return authUser;
        }
        return null;
    }

    public AuthUser register(ContainerRequestContext requestContext) {
        HashMap<String, String> credentials = getCredentials(requestContext);
        if (authUserDAO.findAuthUserByUsername(credentials.get("username")) == null)
        {
            AuthUser authUser = new AuthUser(credentials.get("username"), credentials.get("password"), AuthUserRole.USER);
            authUserDAO.addAuthUser(authUser);
            String jsonWebToken = authenticationService.createJWT(String.valueOf(authUser.getId()), authUser.getUsername());
            // TODO: Create webtoken in DB
            authUser.setToken(jsonWebToken);
            return authUser;
        }
        return null;
    }

    private HashMap<String, String> getCredentials(ContainerRequestContext requestContext) {
        HashMap<String, String> credentials = new HashMap<>();
        if (requestContext != null)
        {
            List<String> authHeader = requestContext.getHeaders().get("Authorization");
            if (authHeader != null)
            {
                String authHeaderCredentialsString = authHeader.get(0);
                String[] credentialStrings = authHeaderCredentialsString.split(":");
                credentials.put("username", credentialStrings[0]);
                credentials.put("password", credentialStrings[1]);
            }
        }
        return credentials;
    }
}
