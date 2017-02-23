package dal;

import com.sun.xml.internal.rngom.binary.DataExceptPattern;
import controller.UserValidator;
import dto.User;

import javax.xml.crypto.Data;
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
        ResultSet resultSet = dbConnection.query("SELECT * FROM user WHERE id = " + userId + " LIMIT 1");
        User user;

        try {
            resultSet.next();
            user = getUserFromResultSet(resultSet);
        } catch (SQLException e) {
            System.err.println("[]: Could not fetch user with id = " + userId);
            e.printStackTrace();
            return null;
        } finally {
            dbConnection.close();
        }

        return user;
    }

    @Override
    public User getUser(String username) throws DataAccessException {
        User user;
        final String sql = String.format("SELECT * FROM user WHERE username = '%s' LIMIT 1", username);
        ResultSet resultSet = dbConnection.query(sql);
        try {
            resultSet.next();
            user = getUserFromResultSet(resultSet);
//            roleId = Integer.parseInt(resultSet.getString("id"));
        } catch (SQLException e) {
            System.err.println("[UserAdministrationDB::getUserRoles]: Could not fetch user.");
            e.printStackTrace();
            return null;
        } finally {
            dbConnection.close();
        }
        return user;
    }

//    public User getUser(String username) throws DataAccessException {
//        String sql = String.format("SELECT * FROM user WHERE username = '%s' LIMIT 1", username);
//        System.out.println(sql);
//        User user;
//
//        ResultSet resultSet = dbConnection.query(sql);
//        try {
//            user = getUserFromResultSet(resultSet);
//        } catch (SQLException e) {
//            return null;
//        }
//        return user;
//    }

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
        } catch (SQLException e) {
            System.err.println("[UserAdministrationDB::getUserFromResultSet]: " + e.getMessage());
            e.printStackTrace();
        }
        catch(Exception e) {
            System.err.println("Invalid datatype for user id.");
        }

        String userName    = resultSet.getString("username");
        String ini         = resultSet.getString("initials");
        List<String> roles = getRolesFromUser(userId);
        String cpr         = resultSet.getString("cpr");
        String password    = resultSet.getString("password");
        return new User(userId, userName, ini, roles, cpr, password);
    }

    private List<String> getRolesFromUser(int userId) {
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
        String username = user.getUserName();
        String initials = user.getInitials();
        List<String> roles = user.getRoles();
        String cpr = user.getCpr();
        String password = user.getPassword().getPassword();

        // Add User
        String sql = String.format("INSERT INTO user (username,initials,cpr,password) " +
                "VALUES ('%s','%s','%s','%s')", username, initials, cpr, password);

        if (!UserValidator.isUsernameValid(username)) throw new DataAccessException("[UserAdministrationDB::createUser]: Username is invalid. Username must be between 2 and 20 characters, and there must be at least 1 non-numeric character.");
        if (!UserValidator.isCprValid(cpr)) throw new DataAccessException("[UserAdministrationDB::createUser]: CPR does not have correct format!");
        if (!UserValidator.isInitialsValid(initials)) throw new DataAccessException("[UserAdministrationDB::createUser]: Initials are either too long or too short (must be between 2 and 4 characters)");

        // Insert user (no roles)
        dbConnection.update(sql);
        dbConnection.close();

        // Get user id
        user = getUser(user.getUserName());

        // Add user's roles
        for (String role : roles)
            addRoleToUser(user, role);
    }

    private void addRoleToUser(User user, String role) {
        int userId = user.getUserId();
        int roleId = getRoleId(role);

        String sql = String.format("INSERT INTO user_role (user_id,role_id) " +
                "VALUES (%d,%d)", userId, roleId);

        dbConnection.update(sql);
        dbConnection.close();
    }

    // TODO: Make this private
    public int getRoleId(String roleName) {
        int roleId;
        ResultSet resultSet = dbConnection.query("SELECT id FROM role WHERE role_name = '" +  roleName + "' LIMIT 1");
        try {
            resultSet.next();
            roleId = Integer.parseInt(resultSet.getString("id"));
        } catch (SQLException e) {
            System.err.println("[UserAdministrationDB::getUserRoles]: Could not fetch role id.");
            e.printStackTrace();
            return -1;
        } finally {
            dbConnection.close();
        }
        return roleId;
    }

    public String[] getUserRoles() throws DataAccessException {
        List<String> userRoles = new ArrayList<>();
        ResultSet resultSet = dbConnection.query("SELECT role_name FROM role");

        try {
            while (resultSet.next())
                userRoles.add(resultSet.getString("role_name"));
        } catch (SQLException e) {
            System.err.println("[UserAdministrationDB::getUserRoles]: Could not fetch user roles.");
            e.printStackTrace();
            return null;
        } finally {
            dbConnection.close();
        }

        return userRoles.stream().toArray(String[]::new);
    }

    @Override
    public void updateUser(User user) throws DataAccessException {
        final User oldUser = getUser(user.getUserId());
        int changeCount = 0;
        String sql  = "UPDATE user SET";
        if (!oldUser.getUserName().equals(user.getUserName())) {// Username has been changed
            if (changeCount > 0) sql += ", ";
            changeCount++;
            sql += " username =  '" + user.getUserName() + "'";
        }
        if (!oldUser.getInitials().equals(user.getInitials())) {// Initials has been changed
            if (changeCount > 0) sql += ", ";
            changeCount++;
            sql += " initials = '" + user.getInitials() + "'";
        }
        if (!oldUser.getPassword().getPassword().equals(user.getPassword().getPassword())) {// TODO: Make equals method in password instead
            if (changeCount > 0) sql += ", ";
            changeCount++;
            sql += " password = '" + user.getPassword().getPassword() + "'";
        }
        if (changeCount == 0) return; // No changes was made
        sql += "WHERE id = " + user.getUserId();

        dbConnection.update(sql);
        dbConnection.close();
    }

    @Override
    public void deleteUser(int userId) throws DataAccessException {
        String sql = String.format("DELETE FROM user WHERE id = %d", userId);
        dbConnection.update(sql);
        dbConnection.close();
    }
}
