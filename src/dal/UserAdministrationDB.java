package dal;

import dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        User user = null;
        dbConnection.prepareQuery("SELECT * FROM user WHERE id=?");
        dbConnection.setPreparedInt(1, userId);

        try {
            user = getUserFromResultSet(dbConnection.executePreparedQuery());
        } catch (SQLException e) {
            user = null;
            System.err.println("[UserAdministrationDB::getUser]: Could not get user.");
            e.printStackTrace();
        } finally {
            dbConnection.close();
            return user;
        }
    }

    @Override
    public List<User> getUserList() throws DataAccessException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = dbConnection.query("SELECT * FROM user");

        try {
            while(resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch(SQLException e) {
            System.err.println("[UserAdministrationDB]: An error occured when trying to fetch all users from the database.");
            e.printStackTrace();
            return null;
        } finally {
            dbConnection.close();
        }

        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = 0;

        try {
            userId = Integer.parseInt(resultSet.getString("id")); // TODO: Make sure all fields are spelled correctly
        } catch(Exception e) {
            System.err.println("Invalid datatype for user id.");
        }

        String userName    = resultSet.getString("username");
        String ini         = resultSet.getString("initials");
        List<String> roles = getRoleFromUser(userId);
        String cpr         = resultSet.getString("cpr");
        String password    = resultSet.getString("password");
        return new User(userId, userName, ini, roles, cpr, password);
    }

    private List<String> getRoleFromUser(int userId) {
        ResultSet resultSet = dbConnection.query("" +
                "SELECT role_name FROM user " +
                "JOIN user_role ON user_role.user_id = user.id " +
                "JOIN role ON role.id = user_role.role_id " +
                "WHERE user.id = " + userId);

        List<String> roles = new ArrayList<>(); //

        try {
            while (resultSet.next()) {
                roles.add(resultSet.getString("role_name"));
            }
        } catch (SQLException e) {
            System.err.println("Invalid SQL Statement");
        } finally {
            dbConnection.close();
        }

        return roles;
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
