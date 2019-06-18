package dao.blueprint;

import models.AuthUser;

import java.util.List;

public interface IAuthUserDAO {
    AuthUser addAuthUser(AuthUser authUser);

    AuthUser updateAuthUser(AuthUser authUser);

    AuthUser findAuthUserByUsername(String username);

    AuthUser findAuthUserByCredentials(String username, String hashedPassword);

    String getUserRole(AuthUser authUser);

    List<AuthUser> getAll();

    void removeAuthUser(AuthUser authUser);
}
