package dao.blueprint;

import models.JsonWebToken;

public interface IJsonWebTokenDAO {
    JsonWebToken addJsonWebToken(JsonWebToken jsonWebToken);

    JsonWebToken findValidJsonWebTokenByTokenString(String tokenString);
}
