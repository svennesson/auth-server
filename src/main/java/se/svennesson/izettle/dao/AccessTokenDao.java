package se.svennesson.izettle.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import se.svennesson.izettle.models.AccessToken;

import java.util.Optional;
import java.util.UUID;

public interface AccessTokenDao {

    @SqlQuery("SELECT * FROM tokens WHERE token = :token")
    AccessToken findByToken(@Bind("token") final UUID token);

    @SqlQuery("INSERT INTO tokens (token, user_id) VALUES (:token, :userId) RETURNING *")
    AccessToken insertAccessToken(@Bind("token") final UUID token, @Bind("userId") final Long userId);
}
