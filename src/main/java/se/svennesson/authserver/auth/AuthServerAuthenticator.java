package se.svennesson.authserver.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import se.svennesson.authserver.exceptions.UnAuthorizedException;
import se.svennesson.authserver.models.AccessToken;
import se.svennesson.authserver.models.User;
import se.svennesson.authserver.services.AccessTokenService;
import se.svennesson.authserver.services.UserService;
import se.svennesson.authserver.utils.AccessTokenUtil;

import java.util.Optional;
import java.util.UUID;

public class AuthServerAuthenticator implements Authenticator<String, User> {

    private final AccessTokenService tokenService;
    private final UserService userService;

    public AuthServerAuthenticator(AccessTokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public Optional<User> authenticate(String tokenString) throws AuthenticationException {
        try {
            final UUID token = UUID.fromString(tokenString);
            final AccessToken accessToken = tokenService.findByToken(token)
                    .filter(AccessTokenUtil::isAccessTokenValid)
                    .orElseThrow(UnAuthorizedException::new);

            return userService.findById(accessToken.getUserId());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
