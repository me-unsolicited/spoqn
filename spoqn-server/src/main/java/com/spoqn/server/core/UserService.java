package com.spoqn.server.core;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mindrot.jbcrypt.BCrypt;
import org.mybatis.guice.transactional.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.spoqn.server.core.exceptions.AuthenticationException;
import com.spoqn.server.core.exceptions.ExistingLoginException;
import com.spoqn.server.core.exceptions.InadequatePasswordException;
import com.spoqn.server.core.exceptions.SpoqnException;
import com.spoqn.server.data.TokenMap;
import com.spoqn.server.data.User;
import com.spoqn.server.data.mappers.UserMapper;

import lombok.NonNull;

@Singleton
@Transactional
public class UserService {

    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final TemporalAmount TOKEN_LIFETIME = Duration.ofMinutes(15L);

    @Inject private UserMapper mapper;

    public User getUser(String loginId) {
        return mapper.get(loginId);
    }

    public User createUser(User user) {

        // check for existing login ID
        String loginId = user.getLoginId();
        if (mapper.get(loginId) != null)
            throw new ExistingLoginException();

        // verify password requirements
        String password = user.getPassword();
        if (password.length() < PASSWORD_MIN_LENGTH)
            throw new InadequatePasswordException();

        // securely generate password hash
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());

        // create the user and store their password hash
        mapper.create(user);
        mapper.createPassword(loginId, hash);

        // return the updated user
        return mapper.get(loginId);
    }

    /**
     * @return Access and refresh tokens
     * @throws AuthenticationException
     *             If authentication has failed
     */
    public TokenMap authenticate(@NonNull String loginId, @NonNull String password, @NonNull String deviceName,
            @NonNull String deviceHash) {

        if (loginId.isEmpty() || password.isEmpty())
            throw new AuthenticationException();

        // get the stored password hash
        String hash = mapper.getPassHash(loginId);
        if (hash == null)
            throw new AuthenticationException();

        // verify the plaintext password against the salted hash
        boolean authenticated = BCrypt.checkpw(password, hash);
        if (!authenticated)
            throw new AuthenticationException();

        // create the device if it doesn't already exist
        String knownDeviceName = mapper.getDeviceName(loginId, deviceHash);
        if (knownDeviceName == null) {
            mapper.createDevice(loginId, deviceName, deviceHash);
            knownDeviceName = deviceName;
        }

        return TokenMap.builder()
                .access(issueAccessToken(loginId))
                .refresh(issueRefreshToken(loginId, knownDeviceName))
                .build();
    }

    /**
     * @return Refreshed access token and existing refresh token
     * @throws AuthenticationException
     *             If authentication has failed
     */
    public TokenMap refresh(@NonNull String loginId, @NonNull String refresh, @NonNull String deviceHash) {

        if (loginId.isEmpty())
            throw new AuthenticationException();

        // verify the device hash
        String deviceName = mapper.getDeviceName(loginId, deviceHash);
        if (deviceName == null)
            throw new AuthenticationException();

        // get the expected token hash for this user and device
        String hash = mapper.getTokenHash(loginId, deviceName);
        if (hash == null)
            throw new AuthenticationException();

        // verify the plaintext token against the salted hash
        boolean authenticated = BCrypt.checkpw(refresh, hash);
        if (!authenticated)
            throw new AuthenticationException();

        return TokenMap.builder()
                .access(issueAccessToken(loginId))
                .refresh(refresh)
                .build();
    }

    public void revoke(@NonNull String username) {

        // TODO delete the refresh tokens from the database
        Set<String> tokens = new HashSet<>();
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

    private String issueRefreshToken(String loginId, String deviceName) {

        // securely generate a token and hash it
        String token = UUID.randomUUID().toString();
        String hash = BCrypt.hashpw(token, BCrypt.gensalt());

        // put the salted hash in the database
        mapper.createToken(loginId, deviceName, hash);

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
