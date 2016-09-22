package se.svennesson.izettle.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.svennesson.izettle.dao.mappers.UserMapper;
import se.svennesson.izettle.models.User;

@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlQuery("INSERT INTO users (name, email, password) VALUES (:name, :email, :password) RETURNING *")
    User createUser(@Bind("name") final String name, @Bind("email") final String email, @Bind("password") final String password);

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    User findById(@Bind("id") final Long id);

    @SqlQuery("SELECT * FROM users WHERE email = :email")
    User findByEmail(@Bind("email") final String email);
}