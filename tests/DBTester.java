/**
 * Created by freya on 23/2/17.
 */

import dal.DBConnection;
import dal.IUserAdministration;
import dal.IUserAdministration.DataAccessException;
import dal.UserAdministrationDB;
import dto.User;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBTester {
    DBConnection dbc = new DBConnection("sql.wiberg.tech", 3306, "CDIO_del1", "hold12", "2017_h0lD!2");
    IUserAdministration iDAO = new UserAdministrationDB(dbc);

    User user1, user2;

    private static void printUsers(IUserAdministration iDAO) {
        try {
            System.out.println("Printing users...");
            List<User> userList = iDAO.getUserList();
            for (User user : userList) {
                System.out.println(user);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void UserCreationDeletionUpdate() {
        printUsers(iDAO);
        System.out.println("");

        //Delete if test users already exist
        dbc.update("DELETE FROM user WHERE cpr IN ('0101010101','0202020202')");

        // Find Max User ID
        int max = 1;
        ResultSet result = dbc.query("SELECT AUTO_INCREMENT\n" +
                "FROM  INFORMATION_SCHEMA.TABLES\n" +
                "WHERE TABLE_SCHEMA = 'CDIO_del1'\n" +
                "AND   TABLE_NAME   = 'user';");
        try {
            if (result.next()) {
                max = Integer.parseInt(result.getString("AUTO_INCREMENT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> roles = new ArrayList<String>();
        user1 = new User(max, "testName", "test", roles, "0101010101");
        user1.addRole("Admin");

        //Creating user1
        try {
            iDAO.createUser(user1);
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (IUserAdministration.DataValidationException e) {
            e.printStackTrace();
        }

        //Trying to create user1 again
        try {
            iDAO.createUser(user1);
        } catch (DataAccessException e1) {
            System.out.println("DataAccessException: User already existed - OK");
        } catch (IUserAdministration.DataValidationException e) {
            System.out.println("DataValidationException: User already existed - OK");
        }

        user2 = new User(max + 1, "2ND user", "ini2", roles, "0202020202");

        //Creating user2
        try {
            iDAO.createUser(user2);
        } catch (DataAccessException e1) {
            e1.printStackTrace();
        } catch (IUserAdministration.DataValidationException e) {
            e.printStackTrace();
        }
        printUsers(iDAO);
        System.out.println("");

        //Updating username
        user2.setUserName("ModifiedName");
        try {
            iDAO.updateUser(user2);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        printUsers(iDAO);
        System.out.println("");

        //Deleting user1
        try {
            iDAO.deleteUser(user1.getUserId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        printUsers(iDAO);
        System.out.println("");

    }

}
