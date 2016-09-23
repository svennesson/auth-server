package se.svennesson.authserver.services;

import se.svennesson.authserver.dao.AccessTokenDao;
import se.svennesson.authserver.models.AccessToken;

import java.util.Optional;
import java.util.UUID;

public class AccessTokenService {

    private final AccessTokenDao tokenDao;

    public AccessTokenService(AccessTokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    public Optional<AccessToken> findByToken(final UUID token) {
        return Optional.ofNullable(tokenDao.findByToken(token));
    }

    AccessToken generateAccessToken(final Long userId) {
        final Optional<AccessToken> tokenByUserId = Optional.ofNullable(tokenDao.findByUserId(userId));
        final UUID generatedID = UUID.randomUUID();
        final AccessToken token;

        if (tokenByUserId.isPresent()) {
            token = tokenDao.updateAccessToken(generatedID, userId);
        } else {
            token = tokenDao.insertAccessToken(generatedID, userId);
        }

        return token;
    }
}
