package dao.jpa;

import dao.blueprint.IJsonWebTokenDAO;
import dao.jpa.config.JPA;
import models.JsonWebToken;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@JPA
@Stateless
@Default
public class JsonWebTokenDAOJPA implements IJsonWebTokenDAO {

    @PersistenceContext(unitName = "localhost")
    private EntityManager em;

    @Override
    public JsonWebToken addJsonWebToken(JsonWebToken jsonWebToken) {
        em.persist(jsonWebToken);
        return jsonWebToken;
    }

    @Override
    public JsonWebToken findValidJsonWebTokenByTokenString(String tokenString) {
        TypedQuery<JsonWebToken> query = em.createNamedQuery("jsonWebToken.getByTokenString", JsonWebToken.class);
        query.setParameter("tokenString", tokenString);
        List<JsonWebToken> results = query.getResultList();
        if (results.size() > 0)
        {
            JsonWebToken token = results.get(0);
            long now = System.currentTimeMillis();
            if (token.getValidUntil() > now)
            {
                return token;
            }
        }
        return null;
    }
}
