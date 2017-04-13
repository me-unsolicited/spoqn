package com.spoqn.server.core;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.spoqn.server.core.exceptions.AuthenticationException;
import com.spoqn.server.core.exceptions.ExistingLoginException;
import com.spoqn.server.core.exceptions.SpoqnException;
import com.spoqn.server.data.entities.Login;
import com.spoqn.server.data.entities.TokenMap;

import lombok.NonNull;

@Component
public class Logins {

    private static final TemporalAmount TOKEN_LIFETIME = Duration.ofMinutes(15L);

    // don't look at this
    private Map<String, String> logins = new HashMap<>();

    /**
     * @throws ExistingLoginException
     *             If a user already exists with the provided username
     */
    public void create(Login login) {

        if (logins.containsKey(login.getUsername())) {
            throw new ExistingLoginException();
        }

        logins.put(login.getUsername(), login.getPassword());
    }

    /**
     * @return Session token
     * @throws AuthenticationException
     *             If authentication has failed
     */
    public TokenMap authenticate(@NonNull String username, @NonNull String password) {

        if (username.isEmpty() || password.isEmpty())
            throw new AuthenticationException();

        boolean authenticated = Objects.equals(password, logins.get(username));
        if (!authenticated)
            throw new AuthenticationException();

        String access = issueToken(username);
        String refresh = "TODO";

        return new TokenMap(access, refresh);
    }

    private String issueToken(String username) {

        Map<String, Object> header = Collections.singletonMap("typ", "JWT");

        Instant now = Instant.now();
        Date issued = Date.from(now);
        Date expiration = Date.from(now.plus(TOKEN_LIFETIME));

        return JWT.create()
                .withHeader(header)
                .withIssuer(issuer())
                .withSubject(username)
                .withIssuedAt(issued)
                .withNotBefore(issued)
                .withExpiresAt(expiration)
                .sign(alg());
    }

    /**
     * @param token
     *            Session token
     * @return Username
     * @throws AuthenticationException
     *             If the token is invalid or expired
     */
    public String resolveUsername(String token) {

        try {
            return JWT.require(alg())
                    .withIssuer(issuer())
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            throw new AuthenticationException(e);
        }
    }

    private Algorithm alg() {
        try {
            return Algorithm.HMAC256(key());
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new SpoqnException(e);
        }
    }

    private String key() {
        // TODO read secret key from file
        return "secret";
    }

    private String issuer() {
        // TODO read issuer from file
        return "spoqn.com";
    }
}
