package models;

import models.hateoas.Link;
import tools.HashTool;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedQueries(value = {
        @NamedQuery(name = "authUser.getByUsername", query = "SELECT a FROM AuthUser a WHERE a.userName = :username"),
        @NamedQuery(name = "authUser.getByCredentials", query = "SELECT a FROM AuthUser a WHERE a.userName = :username AND a.password = :hashedPassword"),
})
@Entity(name = "AuthUser")
public class AuthUser implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String userName;
    @JsonbTransient
    private String password;
    private AuthUserRole authUserRole;
    @Transient
    private String token;
    @Transient
    private List<Link> links = new ArrayList<>();

    public AuthUser() {}

    public AuthUser(String userName, String password, AuthUserRole authUserRole) {
        this.userName = userName;
        this.password = HashTool.getHashedString(password);
        this.authUserRole = authUserRole;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
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

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link)
    {
        links.add(link);
    }
}
