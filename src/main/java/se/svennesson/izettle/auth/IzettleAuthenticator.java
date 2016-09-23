package se.svennesson.izettle.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import se.svennesson.izettle.exceptions.UnAuthorizedException;
import se.svennesson.izettle.models.AccessToken;
import se.svennesson.izettle.models.User;
import se.svennesson.izettle.services.AccessTokenService;
import se.svennesson.izettle.services.UserService;
import se.svennesson.izettle.utils.AccessTokenUtil;

import java.util.Optional;
import java.util.UUID;

public class IzettleAuthenticator implements Authenticator<String, User> {

    private final AccessTokenService tokenService;
    private final UserService userService;

    public IzettleAuthenticator(AccessTokenService tokenService, UserService userService) {
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
