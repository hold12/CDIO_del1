package dal;

import dto.User;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        dbConnection.prepareQuery("SELECT * FROM User WHERE ID=?");
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
        ResultSet resultSet = dbConnection.query("SELECT * FROM User");

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

        return null;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = 0;

        try {
            userId = Integer.parseInt(resultSet.getString("ID")); // TODO: Make sure all fields are spelled correctly
        } catch(Exception e) {
            System.err.println("Invalid datatype for user id.");
        }

        String userName    = resultSet.getString("userName");
        String ini         = resultSet.getString("ini"); // TODO: Refactered to initials?
        List<String> roles = new ArrayList<>(); // TODO: Make a list here
        String cpr         = resultSet.getString("CPR");
        String password    = resultSet.getString("password");

        return new User(userId, userName, ini, roles, cpr, password);
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
