package dal;

/**
 * Created by tjc on 9/2/17.
 */

import dto.User;

import java.util.List;

public interface IUserAdministration {

    public String[] getUserRoles() throws DataAccessException;
    User getUser(int userId) throws DataAccessException;
    User getUser(String username) throws DataAccessException;
    List<User> getUserList() throws DataAccessException;
    void createUser(User user) throws DataAccessException;
    void updateUser(User user) throws DataAccessException;
    void deleteUser(int userId) throws DataAccessException;

    public class DataAccessException extends Exception {

        /**
         *
         */

        public DataAccessException(String msg, Throwable e) {
            super(msg,e);
        }

        public DataAccessException(String msg) {
            super(msg);
        }

    }

}
