package com.spoqn.server.core;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.inject.Singleton;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.spoqn.server.core.exceptions.AuthenticationException;
import com.spoqn.server.core.exceptions.ExistingLoginException;
import com.spoqn.server.core.exceptions.InadequatePasswordException;
import com.spoqn.server.core.exceptions.SpoqnException;
import com.spoqn.server.data.TokenMap;

import lombok.NonNull;

@Singleton
public class LoginService {

    private static final int PASSWORD_MIN_LENGTH = 8;

    private static final TemporalAmount TOKEN_LIFETIME = Duration.ofMinutes(15L);

    // don't look at this
    private Map<String, String> logins = new HashMap<>();
    private Map<String, Set<String>> refreshTokens = new HashMap<>();

    /**
     * @throws ExistingLoginException
     *             If a user already exists with the provided username
     */
    public void create(@NonNull String username, @NonNull String password) {

        if (logins.containsKey(username)) {
            throw new ExistingLoginException();
        }

        validatePassword(password);

        logins.put(username, password);
    }

    private void validatePassword(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH)
            throw new InadequatePasswordException();
    }

    /**
     * @return Access and refresh tokens
     * @throws AuthenticationException
     *             If authentication has failed
     */
    public TokenMap authenticate(@NonNull String username, @NonNull String password) {

        if (username.isEmpty() || password.isEmpty())
            throw new AuthenticationException();

        boolean authenticated = Objects.equals(password, logins.get(username));
        if (!authenticated)
            throw new AuthenticationException();

        return TokenMap.builder()
                .access(issueAccessToken(username))
                .refresh(issueRefreshToken(username))
                .build();
    }

    /**
     * @return Refreshed access token and existing refresh token
     * @throws AuthenticationException
     *             If authentication has failed
     */
    public TokenMap refresh(@NonNull String username, @NonNull String refresh) {

        if (username.isEmpty())
            throw new AuthenticationException();

        Set<String> tokens = refreshTokens.get(username);
        if (tokens == null || !tokens.contains(refresh))
            throw new AuthenticationException();

        return TokenMap.builder()
                .access(issueAccessToken(username))
                .refresh(refresh)
                .build();
    }

    public void revoke(@NonNull String username) {

        Set<String> tokens = refreshTokens.get(username);
        if (tokens != null)
            tokens.clear();
    }

    private String issueAccessToken(String username) {

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

    private String issueRefreshToken(String username) {

        // not thread safe but... this is temporary until db so... :|

        Set<String> tokens = refreshTokens.get(username);
        if (tokens == null) {
            tokens = new HashSet<>();
            refreshTokens.put(username, tokens);
        }

        String token = UUID.randomUUID().toString();
        tokens.add(token);

        return token;
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
