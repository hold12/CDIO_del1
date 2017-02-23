package dal;

/**
 * Created by tjc on 9/2/17.
 */

import dto.User;

import java.util.List;

public interface IUserAdministration {

    String[] getUserRoles() throws DataAccessException;

    User getUser(int userId) throws DataAccessException;

    User getUser(String username) throws DataAccessException;

    List<User> getUserList() throws DataAccessException;

    void createUser(User user) throws DataAccessException, DataValidationException;

    void updateUser(User user) throws DataAccessException;

    void deleteUser(int userId) throws DataAccessException;

    class DataAccessException extends Exception {
        public DataAccessException(String msg, Throwable e) {
            super(msg, e);
        }

        public DataAccessException(String msg) {
            super(msg);
        }

    }

    class DataValidationException extends Exception {
        public DataValidationException(String msg, Throwable e) {
            super(msg, e);
        }

        public DataValidationException(String msg) {
            super(msg);
        }

    }
}
