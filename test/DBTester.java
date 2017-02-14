/**
 * Created by tjc on 9/2/17.
 */

import java.util.List;

import dal.IUserAdministration;
import dal.IUserAdministration.DataAccessException;
import dal.UserDAODiscImpl;
import dto.User;

public class DBTester {
    //TODO refactor as JUnit test???
    public static void main(String[] args) {
        IUserAdministration iDAO = new UserDAODiscImpl();
        User newUser = new User();
        printUsers(iDAO);
        //TODO test new fields...
        newUser.setIni("test");
        newUser.addRole("Admin");
        newUser.setUserName("testName");
        newUser.setUserId(0);
        try {
            iDAO.createUser(newUser);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        try {
            iDAO.createUser(newUser);
        } catch (DataAccessException e1) {
            System.out.println("User already existed - OK");
        }

        newUser.setUserId(1);
        newUser.setUserName("2ND user");
        try {
            iDAO.createUser(newUser);
        } catch (DataAccessException e1) {
            e1.printStackTrace();
        }
        printUsers(iDAO);
        newUser.setUserId(0);
        newUser.setUserName("ModifiedName");
        try {
            iDAO.updateUser(newUser);
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        printUsers(iDAO);

        try {
            iDAO.deleteUser(1);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        printUsers(iDAO);


    }

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

}
