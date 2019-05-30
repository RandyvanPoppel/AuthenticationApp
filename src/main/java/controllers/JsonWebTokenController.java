package controllers;

import models.AuthUser;
import services.JsonWebTokenService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("jsonwebtoken")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class JsonWebTokenController {

    @Inject
    JsonWebTokenService jsonWebTokenService;

    @GET
    @Path("validate")
    public AuthUser getAuthUserByValidJsonWebTokenString(@QueryParam("tokenString") final String tokenString)
    {
        return jsonWebTokenService.getAuthUserByValidJsonWebTokenString(tokenString);
    }
}
