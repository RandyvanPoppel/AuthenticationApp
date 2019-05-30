package services;

import dao.blueprint.IJsonWebTokenDAO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.AuthUser;
import models.JsonWebToken;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Stateless
public class AuthenticationService {

    @Inject
    JsonWebTokenService jsonWebTokenService;

    private static long TOKENLIFETIMEINMILLIS = 18000000; // 5 Hours

    public AuthenticationService() {
    }

    //Sample method to construct a JWT
    public String createJWT(AuthUser authUser) {

        String secretKey = "RatherAppAuth";

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(String.valueOf(authUser.getId()))
                .setIssuedAt(now)
                .setSubject(authUser.getUsername())
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        long expMillis = nowMillis + TOKENLIFETIMEINMILLIS;
        if (TOKENLIFETIMEINMILLIS >= 0) {
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        String tokenString = builder.compact();

        jsonWebTokenService.addJsonWebToken(tokenString, expMillis, authUser);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return tokenString;
    }
}
