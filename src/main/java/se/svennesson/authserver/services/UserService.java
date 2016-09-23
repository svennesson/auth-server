package se.svennesson.authserver.services;

import se.svennesson.authserver.dao.UserDAO;
import se.svennesson.authserver.exceptions.BadCredentialsException;
import se.svennesson.authserver.exceptions.EntityAlreadyExistsException;
import se.svennesson.authserver.models.AccessToken;
import se.svennesson.authserver.models.BasicCredentials;
import se.svennesson.authserver.models.User;
import se.svennesson.authserver.utils.PBKDF2Hash;

import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;
    private final LoginAttemptsService loginAttemptsService;
    private final AccessTokenService tokenService;

    public UserService(final UserDAO userDAO, final LoginAttemptsService loginAttemptsService, final AccessTokenService tokenService) {
        this.userDAO = userDAO;
        this.loginAttemptsService = loginAttemptsService;
        this.tokenService = tokenService;
    }

    public User registerUser(final User user) {
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException(user.getEmail());
        }

        user.setPassword(PBKDF2Hash.createHash(user.getPassword()));
        return userDAO.createUser(user.getName(), user.getEmail(), user.getPassword());
    }

    public Optional<User> findById(final Long id) {
        return Optional.ofNullable(userDAO.findById(id));
    }

    public AccessToken loginUser(BasicCredentials credentials) {
        final User persistedUser = findByEmail(credentials.getEmail()).orElseThrow(BadCredentialsException::new);

        if (PBKDF2Hash.validatePassword(credentials.getPassword(), persistedUser.getPassword())) {
            final AccessToken accessToken = tokenService.generateAccessToken(persistedUser.getId());
            loginAttemptsService.insertLoginAttempt(persistedUser.getId(), true);

            return accessToken;
        }

        loginAttemptsService.insertLoginAttempt(persistedUser.getId(), false);
        throw new BadCredentialsException();
    }

    private Optional<User> findByEmail(final String email) {
        return Optional.ofNullable(userDAO.findByEmail(email));
    }

}
