package controller;

import dal.DBConnection;
import dal.IUserAdministration;
import dal.UserAdministrationDB;
import dto.User;
import ui.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by freya on 14-02-2017.
 */
public class UIController {
    private UI ui;
    private UserAdministrationDB userAdm;

    public UIController(UI ui, UserAdministrationDB userAdm) {
        this.ui = ui;
        this.userAdm = userAdm;
    }

    public void run() {
        mainMenu();
    }

    public void mainMenu() {
        final String[] menuOptions = {
                "Create User",
                "Show Users",
                "Edit User",
                "Delete User",
                "Exit Program"
        };

        String userChoice = ui.getMenuChoice(menuOptions);

        // Create User
        if (userChoice.equals(menuOptions[0])) {
            try {
                userAdm.createUser(createUser());
            } catch (IUserAdministration.DataAccessException e) {

            }
        }
        // Show Users
        else if (userChoice.equals(menuOptions[1])) {
            List<User> users = new ArrayList<>();
            try {
                users = userAdm.getUserList();
            } catch (IUserAdministration.DataAccessException e) {

            }

            for (User user : users)
                System.out.println(user);
        }
        // TEST
        else if (userChoice.equals(menuOptions[menuOptions.length - 1]))
            testMethod();
    }

    private void testMethod() {
        User user;
        try {
            System.out.println("Role id for admin = " + userAdm.getRoleId("admin"));
            user = userAdm.getUser("user11");
        } catch (IUserAdministration.DataAccessException e) {
            return;
        }
//        System.out.println(user.getUserName());
    }

    private User createUser() {
        String username;
        String initials;
        String cpr = "";
        String[] roles;

        username = ui.getUserInput("Enter a username");
        initials = ui.getUserInput("Enter initials");
        while (!CprValidation.isCprValid(cpr))
            cpr = ui.getUserInput("Enter CPR");
        outputRoles();
        roles = ui.getUserInput("Enter roles for user, separated by comma (,)").split(",");

        return new User(-1, username, initials, Arrays.asList(roles), cpr);
    }


    // TODO: Should not be in this class. Just here for convinience while debugging.
    private void outputRoles() {
        String[] roles;
        try {
            roles = userAdm.getUserRoles();
            System.out.print("|");
            for (String role : roles) {
                System.out.print(" " + role + " |");
            }
            System.out.println();
        } catch (IUserAdministration.DataAccessException e) {

        }

    }
}
