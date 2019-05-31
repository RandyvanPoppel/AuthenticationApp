package controllers;

import controllers.hateoas.HATEOAS;
import models.AuthUser;
import models.hateoas.RequestMethod;
import services.AuthUserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless
@Path("authuser")
public class AuthUserController {

    @Inject
    AuthUserService authUserService;

    @POST
    @Path("login")
    public Response login(ContainerRequestContext requestContext,
                          @Context UriInfo uriInfo) {
        AuthUser returnUser = authUserService.login(requestContext);
        returnUser.addLink(HATEOAS.createLink(AuthUserController.class, uriInfo, "self", "login", RequestMethod.POST, new String[]{"Authorization: user:password"}, new String[]{}));
        returnUser.addLink(HATEOAS.createLink(AuthUserController.class, uriInfo, "register", "register", RequestMethod.POST, new String[]{"Authorization: user:password"}, new String[]{}));
        if (returnUser != null) {
            return Response.status(Response.Status.OK)
                    .entity(returnUser)
                    .type(MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("register")
    public Response register(ContainerRequestContext requestContext,
                             @Context UriInfo uriInfo) {
        AuthUser returnUser = authUserService.register(requestContext);
        returnUser.addLink(HATEOAS.createLink(AuthUserController.class, uriInfo, "self", "register", RequestMethod.POST, new String[]{"Authorization: user:password"}, new String[]{}));
        returnUser.addLink(HATEOAS.createLink(AuthUserController.class, uriInfo, "login", "login", RequestMethod.POST, new String[]{"Authorization: user:password"}, new String[]{}));
        if (returnUser != null) {
            return Response.status(Response.Status.OK)
                    .entity(returnUser)
                    .type(MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
