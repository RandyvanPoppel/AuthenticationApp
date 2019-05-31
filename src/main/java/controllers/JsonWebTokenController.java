package controllers;

import controllers.hateoas.HATEOAS;
import models.AuthUser;
import models.hateoas.RequestMethod;
import services.JsonWebTokenService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Stateless
@Path("jsonwebtoken")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class JsonWebTokenController {

    @Inject
    JsonWebTokenService jsonWebTokenService;

    @GET
    @Path("validate")
    public AuthUser getAuthUserByValidJsonWebTokenString(@QueryParam("tokenString") final String tokenString,
                                                         @Context UriInfo uriInfo)
    {
        AuthUser authUser = jsonWebTokenService.getAuthUserByValidJsonWebTokenString(tokenString);
        if (authUser != null)
        {
            authUser.addLink(HATEOAS.createLink(JsonWebTokenController.class, uriInfo, "self", "validate", RequestMethod.GET, new String[]{}, new String[]{"tokenString"}));
        }
        return authUser;
    }
}
