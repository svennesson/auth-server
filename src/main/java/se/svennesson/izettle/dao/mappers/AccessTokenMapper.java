package se.svennesson.izettle.dao.mappers;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.svennesson.izettle.models.AccessToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccessTokenMapper implements ResultSetMapper<AccessToken>{

    @Override
    public AccessToken map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new AccessToken(UUID.fromString(resultSet.getString("token")), resultSet.getTimestamp("distributed_time").toLocalDateTime(), resultSet.getLong("user_id"));
    }
}
