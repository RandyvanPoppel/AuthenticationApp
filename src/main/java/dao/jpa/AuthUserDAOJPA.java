package dao.jpa;

import dao.blueprint.IAuthUserDAO;
import dao.jpa.config.JPA;
import models.AuthUser;
import tools.HashTool;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@JPA
@Stateless
@Default
public class AuthUserDAOJPA implements IAuthUserDAO {

    @PersistenceContext(unitName = "localhost")
    private EntityManager em;

    @Override
    public AuthUser addAuthUser(AuthUser authUser) {
        em.persist(authUser);
        return authUser;
    }

    @Override
    public AuthUser updateAuthUser(AuthUser authUser) {
        em.merge(authUser);
        return authUser;
    }

    @Override
    public AuthUser findAuthUserByUsername(String username) {
        TypedQuery<AuthUser> query = em.createNamedQuery("authUser.getByUsername", AuthUser.class);
        query.setParameter("username", username);
        List<AuthUser> results = query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public AuthUser findAuthUserByCredentials(String username, String hashedPassword) {
        TypedQuery<AuthUser> query = em.createNamedQuery("authUser.getByCredentials", AuthUser.class);
        query.setParameter("username", username);
        query.setParameter("hashedPassword", hashedPassword);
        List<AuthUser> results = query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public String getUserRole(AuthUser authUser) {
        AuthUser authenticatedAuthUser = findAuthUserByCredentials(authUser.getUserName(), HashTool.getHashedString(authUser.getPassword()));
        if (authenticatedAuthUser != null) {
            switch (authenticatedAuthUser.getAuthUserRole()) {
                case USER:
                    return "USER";
                case ADMIN:
                    return "ADMIN";
            }
        }
        return null;
    }
}
