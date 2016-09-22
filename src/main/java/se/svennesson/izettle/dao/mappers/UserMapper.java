package se.svennesson.izettle.dao.mappers;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.svennesson.izettle.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements ResultSetMapper<User>{

    @Override
    public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"));
    }
}
