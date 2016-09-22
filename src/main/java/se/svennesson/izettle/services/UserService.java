package se.svennesson.izettle.services;

import se.svennesson.izettle.dao.UserDAO;
import se.svennesson.izettle.exceptions.EntityAlreadyExistsException;
import se.svennesson.izettle.models.User;
import se.svennesson.izettle.utils.PBKDF2Hash;

import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;

    public UserService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(final User user) {
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException(user.getEmail());
        }

        user.setPassword(PBKDF2Hash.createHash(user.getPassword()));
        return userDAO.createUser(user.getName(), user.getEmail(), user.getPassword());
    }

    public Optional<User> findById(final Long id) {
        return Optional.ofNullable(userDAO.findById(id));
    }

    private Optional<User> findByEmail(final String email) {
        return Optional.ofNullable(userDAO.findByEmail(email));
    }
}
