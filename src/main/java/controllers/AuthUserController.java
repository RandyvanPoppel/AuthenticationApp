package controllers;

import models.AuthUser;
import services.AuthUserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("authuser")
public class AuthUserController {

    @Inject
    AuthUserService authUserService;

    @POST
    @Path("login")
    public Response login(ContainerRequestContext requestContext) {
        AuthUser returnUser = authUserService.login(requestContext);
        if (returnUser != null)
        {
            return Response.status(Response.Status.OK)
                    .entity(returnUser)
                    .type(MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("register")
    public Response register(ContainerRequestContext requestContext) {
        AuthUser returnUser = authUserService.register(requestContext);
        if (returnUser != null)
        {
            return Response.status(Response.Status.OK)
                    .entity(returnUser)
                    .type(MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("validateToken")
    public boolean validateToken(ContainerRequestContext requestContext) {

        return false;
    }
}
