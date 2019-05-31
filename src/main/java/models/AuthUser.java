package models;

import tools.HashTool;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;

@NamedQueries(value = {
        @NamedQuery(name = "authUser.getByUsername", query = "SELECT a FROM AuthUser a WHERE a.username = :username"),
        @NamedQuery(name = "authUser.getByCredentials", query = "SELECT a FROM AuthUser a WHERE a.username = :username AND a.password = :hashedPassword"),
})
@Entity(name = "AuthUser")
public class AuthUser implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String username;
    @JsonbTransient
    private String password;
    private AuthUserRole authUserRole;
    @Transient
    private String token;

    public AuthUser() {}

    public AuthUser(String username, String password, AuthUserRole authUserRole) {
        this.username = username;
        this.password = HashTool.getHashedString(password);
        this.authUserRole = authUserRole;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthUserRole getAuthUserRole() {
        return authUserRole;
    }

    public void setAuthUserRole(AuthUserRole authUserRole) {
        this.authUserRole = authUserRole;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
