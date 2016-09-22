package se.svennesson.izettle.services;

import se.svennesson.izettle.dao.AccessTokenDao;
import se.svennesson.izettle.models.AccessToken;

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

    public AccessToken generateAccessToken(final Long userId) {
        final UUID generatedID = UUID.randomUUID();
        return tokenDao.insertAccessToken(generatedID, userId);
    }
}
