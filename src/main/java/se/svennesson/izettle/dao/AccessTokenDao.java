package se.svennesson.izettle.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.svennesson.izettle.dao.mappers.AccessTokenMapper;
import se.svennesson.izettle.models.AccessToken;

import java.util.Optional;
import java.util.UUID;

@RegisterMapper(AccessTokenMapper.class)
public interface AccessTokenDao {

    @SqlQuery("SELECT * FROM tokens WHERE token = :token")
    AccessToken findByToken(@Bind("token") final UUID token);

    @SqlQuery("INSERT INTO tokens (token, user_id) VALUES (:token, :userId) RETURNING *")
    AccessToken insertAccessToken(@Bind("token") final UUID token, @Bind("userId") final Long userId);

    @SqlQuery("SELECT * FROM tokens WHERE user_id = :userId")
    AccessToken findByUserId(@Bind("userId") final Long userId);

    @SqlQuery("UPDATE tokens SET token = :token, distributed_time = NOW() WHERE user_id = :userId RETURNING *")
    AccessToken updateAccessToken(@Bind("token") final UUID generatedID, @Bind("userId") final Long userId);
}
