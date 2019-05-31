package services;

import dao.blueprint.IAuthUserDAO;
import models.AuthUser;
import models.AuthUserRole;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartUpService {
    @Inject
    IAuthUserDAO authUserDAO;

    @PostConstruct
    private void initStart()
    {
        AuthUser user = new AuthUser("user", "userpw", AuthUserRole.USER);
        authUserDAO.addAuthUser(user);
        AuthUser admin = new AuthUser("admin", "adminpw", AuthUserRole.ADMIN);
        authUserDAO.addAuthUser(admin);
}
}
