package dal;

import dto.User;

import java.util.List;

/**
 * Created by freya on 14-02-2017.
 */
public class UserAdministrationDB implements IUserAdministration {
    private DBConnection dbConnection;

    public UserAdministrationDB(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public User getUser(int userId) throws DataAccessException {
        return null;
    }

    @Override
    public List<User> getUserList() throws DataAccessException {
        return null;
    }

    @Override
    public void createUser(User user) throws DataAccessException {

    }

    @Override
    public void updateUser(User user) throws DataAccessException {

    }

    @Override
    public void deleteUser(int userId) throws DataAccessException {

    }
}
