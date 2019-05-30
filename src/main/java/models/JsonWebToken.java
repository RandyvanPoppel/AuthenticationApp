package models;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries(value = {
        @NamedQuery(name = "jsonWebToken.getByTokenString", query = "SELECT j FROM JsonWebToken j WHERE j.tokenString = :tokenString"),
})
@Entity(name = "JsonWebToken")
public class JsonWebToken implements Serializable {

    @Id
    @GeneratedValue
    private String id;
    private String tokenString;
    private long validUntil;
    @OneToOne
    private AuthUser authUser;

    public JsonWebToken() {}

    public JsonWebToken(String tokenString, long validUntil, AuthUser authUser) {
        this.tokenString = tokenString;
        this.validUntil = validUntil;
        this.authUser = authUser;
    }

    public String getId() {
        return id;
    }

    public String getTokenString() {
        return tokenString;
    }

    public long getValidUntil() {
        return validUntil;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }
}
