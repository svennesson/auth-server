package se.svennesson.izettle.services;

import se.svennesson.izettle.dao.LoginAttemptDAO;
import se.svennesson.izettle.models.LoginAttempt;

import java.util.List;

public class LoginAttemptsService {

    private final LoginAttemptDAO loginAttemptDAO;

    public LoginAttemptsService(LoginAttemptDAO loginAttemptDAO) {
        this.loginAttemptDAO = loginAttemptDAO;
    }

    void insertLoginAttempt(final Long userId, final boolean success) {
        loginAttemptDAO.insertLoginAttempt(userId, success);
    }

    public List<LoginAttempt> getLatestSuccessfulAttempts(final Long userId) {
        return loginAttemptDAO.getLatestSuccessfulAttempts(userId);
    }
}
