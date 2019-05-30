package dao.blueprint;

import models.AuthUser;

public interface IAuthUserDAO {
    AuthUser addAuthUser(AuthUser authUser);

    AuthUser updateAuthUser(AuthUser authUser);

    AuthUser findAuthUserByUsername(String username);

    AuthUser findAuthUserByCredentials(String username, String hashedPassword);
}
