package com.spoqn.server.core;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.spoqn.server.core.exceptions.AuthenticationException;
import com.spoqn.server.core.exceptions.ExistingLoginException;
import com.spoqn.server.data.entities.Login;

@Component
public class Logins {

    // don't look at this
    private Map<String, String> logins = new HashMap<>();
    private Map<String, String> tokens = new HashMap<>();

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
    public String authenticate(Login login) {

        String username = login.getUsername();
        String password = login.getPassword();
        boolean authenticated = Objects.equals(password, logins.get(username));

        if (!authenticated)
            throw new AuthenticationException();

        return issueToken(username);
    }

    private String issueToken(String username) {
        String token = generateToken(username);
        tokens.put(token, username);
        return token;
    }

    private String generateToken(String username) {

        try {
            Algorithm alg = Algorithm.HMAC256("TODO: secret");
            return JWT.create().withIssuer("spoqn.com").withSubject(username).sign(alg);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            // TODO throw a spoqn-exception
            throw new RuntimeException(e);
        }
    }

    /**
     * @param token
     *            Session token
     * @return Username
     * @throws AuthenticationException
     *             If the token is invalid or expired
     */
    public String resolveUsername(String token) {

        String username = tokens.get(token);
        if (username == null)
            throw new AuthenticationException();

        return username;
    }
}
