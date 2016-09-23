package se.svennesson.izettle.dao.mappers;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.svennesson.izettle.models.LoginAttempt;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginAttemptsMapper implements ResultSetMapper<LoginAttempt>{

    @Override
    public LoginAttempt map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new LoginAttempt(resultSet.getLong("id"), resultSet.getLong("user_id"), resultSet.getBoolean("success"), resultSet.getTimestamp("timestampz").toLocalDateTime());
    }
}
