package se.svennesson.authserver.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.svennesson.authserver.dao.mappers.LoginAttemptsMapper;
import se.svennesson.authserver.models.LoginAttempt;

import java.util.List;

@RegisterMapper(LoginAttemptsMapper.class)
public interface LoginAttemptDAO {

    @SqlUpdate("INSERT INTO login_attempts (user_id, success) VALUES (:userId, :success)")
    void insertLoginAttempt(@Bind("userId")final Long userId, @Bind("success") final boolean success);

    @SqlQuery("SELECT * FROM login_attempts WHERE user_id = :userId AND success = true ORDER BY timestampz DESC LIMIT 5")
    List<LoginAttempt> getLatestSuccessfulAttempts(@Bind("userId") final Long userId);
}
