package controller;

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
        while (true) {
            final String[] menuOptions = {
                    "Create User",
                    "Show Users",
                    "Edit User",
                    "Delete User",
                    "Exit Program"
            };

            final String userChoice = ui.getMenuChoice(menuOptions);

            // Create User
            if (userChoice.equals(menuOptions[0])) {
                createUser();
            }
            // Show Users
            else if (userChoice.equals(menuOptions[1])) {
                listUsers();
            }
            // Edit User
            else if (userChoice.equals(menuOptions[2])) {
                editUser();
            }
            // Delete User
            else if (userChoice.equals(menuOptions[3])) {
                deleteUser();
            }
            // Exit
            else if (userChoice.equals(menuOptions[menuOptions.length - 1]))
                return;
        }
    }

    private void listUsers() {
        List<User> users = new ArrayList<>();
        try {
            users = userAdm.getUserList();
        } catch (IUserAdministration.DataAccessException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        for (User user : users)
            ui.printMsg(user.toString());
    }

    private void createUser() {
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

        try {
            userAdm.createUser(new User(-1, username, initials, Arrays.asList(roles), cpr));
        } catch (IUserAdministration.DataAccessException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    private void deleteUser() {
        final User user = getUser();
        ui.printMsg("Selected user: " + user.toString());

        String verfication = ui.getUserInput("Are you sure? (Y/n)");
        if (verfication.toLowerCase().equals("y") || verfication.equals("")) {
            try {
                userAdm.deleteUser(user.getUserId());
            } catch (IUserAdministration.DataAccessException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    private void editUser() {
        ui.printMsg("What user do you want to edit?");

        final User user = getUser();
        final String[] menuOptions = {
                "Change Username",
                "Change Initials",
                "Generate New Password",
                /*"Add Roles",*/
                /*"Remove Roles",*/
                /*"Change CPR"*/
                "Finish Editing"
        };

        ui.printMsg("Changing user: " + user.toString());

        while (true) {
            String newUserChoice = ui.getMenuChoice(menuOptions);

            if (newUserChoice.equals(menuOptions[0])) // Change Username
                changeUsername(user);
            if (newUserChoice.equals(menuOptions[1])) // Change Initials
                changeInitials(user);
            if (newUserChoice.equals(menuOptions[2])) // Generate New Password
                user.generateNewPassword();
            if (newUserChoice.equals(menuOptions[menuOptions.length - 1])) {
                try {
                    userAdm.updateUser(user);
                } catch (IUserAdministration.DataAccessException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                    return;
                }
                break;
            }
        }
        ui.printMsg("");
    }

    private void changeUsername(User user) {
        ui.printMsg("Current username = " + user.getUserName());
        String newUsername = ui.getUserInput("Type a new username");
        user.setUserName(newUsername);
        ui.printMsg("Change username to " + user.getUserName());
    }

    private void changeInitials(User user) {
        ui.printMsg("Current initials = " + user.getInitials());
        String newInitials = ui.getUserInput("Type the new initials");
        user.setInitials(newInitials);
        ui.printMsg("Change initials to " + user.getInitials());
    }

    private User getUser() {
        User user;
        int userId = -1;
        String input;
        try {
            input = ui.getUserInput("Select a user by name or id");
            try {
                userId = Integer.parseInt(input);
                if (userId > 99 || userId < 11) {
                    return userAdm.getUser(input);
                }
            } catch (Exception e) {}
            if (userId != -1)
                return userAdm.getUser(userId);
            else
                return userAdm.getUser(input);
        } catch (IUserAdministration.DataAccessException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    // TODO: Should not be in this class. Just here for convinience while debugging.
    private void outputRoles() {
        String[] roles;
        try {
            roles = userAdm.getUserRoles();
        } catch (IUserAdministration.DataAccessException e) {
            // TODO: Add catch
            return;
        }

        ui.showRoles(roles);
    }
}
