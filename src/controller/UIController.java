package controller;

import dal.IUserAdministration;
import dal.UserAdministrationDB;
import dto.User;
import lang.Lang;
import ui.UI;

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
                    Lang.msg("createUser"),
                    Lang.msg("showUsers"),
                    Lang.msg("editUser"),
                    Lang.msg("deleteUser"),
                    Lang.msg("exitProgram")
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
        List<User> users;
        try {
            users = userAdm.getUserList();
        } catch (IUserAdministration.DataAccessException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        for (User user : users)
            ui.printMsg(user.toString());

        ui.printMsg("");
    }

    private void createUser() {
        String username = "";
        String initials = "";
        String cpr = "";
        String[] roles;

        while (!UserValidator.isUsernameValid(username)) username = ui.getUserInput(Lang.msg("enterUsername"));
        while (!UserValidator.isInitialsValid(initials)) initials = ui.getUserInput(Lang.msg("enterInitials"));
        while (!UserValidator.isCprValid(cpr))
            cpr = ui.getUserInput(Lang.msg("enterCpr"));
        outputRoles();
        roles = ui.getUserInput(Lang.msg("enterRoles")).split(",");

        try {
            userAdm.createUser(new User(-1, username, initials, Arrays.asList(roles), cpr));
        } catch (IUserAdministration.DataAccessException e) {
            ui.printError(Lang.msg("errorCreateUser") + "\n" + e.getMessage());
            return;
        }
    }

    private void deleteUser() {
        final User user = getUser();
        ui.printMsg(Lang.msg("selectedUser") + ": " + user.toString());

        String verification = ui.getUserInput(Lang.msg("confirmation"));
        if (verification.toLowerCase().equals(Lang.msg("yes")) || verification.equals("")) {
            try {
                userAdm.deleteUser(user.getUserId());
            } catch (IUserAdministration.DataAccessException e) {
                ui.printError(Lang.msg("errorDeleteUser") + "\n" + e.getMessage());
                return;
            }
        }
    }

    private void editUser() {
        ui.printMsg(Lang.msg("chooseUserToEdit"));

        final User user = getUser();
        final String[] menuOptions = {
                Lang.msg("changeUsername"),
                Lang.msg("changeInitials"),
                Lang.msg("generateNewPassword"),
                Lang.msg("addRoles"),
                Lang.msg("removeRoles"),
                Lang.msg("cancel"),
                Lang.msg("finishEditing")
        };

        ui.printMsg(Lang.msg("selectedUser") + ": " + user.toString());

        while (true) {
            String newUserChoice = ui.getMenuChoice(menuOptions);

            if (newUserChoice.equals(menuOptions[0])) // Change Username
                changeUsername(user);
            if (newUserChoice.equals(menuOptions[1])) // Change Initials
                changeInitials(user);
            if (newUserChoice.equals(menuOptions[2])) // Generate New Password
                user.generateNewPassword();
            if (newUserChoice.equals(menuOptions[3])) // Add roles
                addRolesToUser(user);
            if (newUserChoice.equals(menuOptions[4])) // Remove roles
                removeRoleFromUser(user);
            if (newUserChoice.equals(menuOptions[menuOptions.length - 2]))
                break;
            if (newUserChoice.equals(menuOptions[menuOptions.length - 1])) {
                try {
                    userAdm.updateUser(user);
                } catch (IUserAdministration.DataAccessException e) {
                    ui.printError(Lang.msg("errorEditUser") + "\n" + e.getMessage());
                    return;
                }
                break;
            }
        }
        ui.printMsg("");
    }

    private void changeUsername(User user) {
        ui.printMsg(Lang.msg("currentUsername") + " = " + user.getUserName());
        String newUsername = "";

        while (!UserValidator.isUsernameValid(newUsername))
            newUsername = ui.getUserInput(Lang.msg("enterUsername"));
        user.setUserName(newUsername);
        ui.printMsg(Lang.msg("usernameChangedTo") + ": " + user.getUserName());
    }

    private void changeInitials(User user) {
        ui.printMsg(Lang.msg("currentInitials") + " = " + user.getInitials());
        String newInitials = "";

        while (!UserValidator.isInitialsValid(newInitials))
            newInitials = ui.getUserInput(Lang.msg("enterInitials"));
        user.setInitials(newInitials);
        ui.printMsg(Lang.msg("initialsChangedTo") + ": " + user.getInitials());
    }

    private void addRolesToUser(User user) {
        ui.printMsg(Lang.msg("availableRoles"));
        outputRoles();

        String currentRolesMsg = "\"" + user.getUserName() + "\" " + Lang.msg("currentRolesAssigned") + ": ";

        if (user.getRoles().size() == 0)
            currentRolesMsg += Lang.msg("none");

        for (int i = 0; i < user.getRoles().size(); i++) {
            if (i == 0)
                currentRolesMsg += user.getRoles().get(i);
            else
                currentRolesMsg += ", " + user.getRoles().get(i);
        }

        ui.printMsg(currentRolesMsg);

        String[] selectedRoles = ui.getUserInput(Lang.msg("enterRoles")).split(",");
        selectedRoles = UserValidator.removeDuplicateRoles(user, selectedRoles);

        for (String role : selectedRoles)
            userAdm.addRoleToUser(user, role);
    }

    private void removeRoleFromUser(User user) {
        String currentRolesMsg = "\"" + user.getUserName() + "\"" + Lang.msg("currentRolesAssigned") + ": ";

        if (user.getRoles().size() == 0) {
            currentRolesMsg += Lang.msg("none");
            return;
        }

        for (int i = 0; i < user.getRoles().size(); i++) {
            if (i == 0)
                currentRolesMsg += user.getRoles().get(i);
            else
                currentRolesMsg += ", " + user.getRoles().get(i);
        }

        ui.printMsg(currentRolesMsg);

        String[] selectedRoles = ui.getUserInput(Lang.msg("enterRoles")).split(",");
        selectedRoles = UserValidator.ensureRoles(user, selectedRoles);

        for (String role : selectedRoles)
            userAdm.removeRoleFromUser(user, role);
    }

    private User getUser() {
        int userId = -1;
        String input;
        try {
            input = ui.getUserInput(Lang.msg("selectUser"));
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
            ui.printError(Lang.msg("errorFindUser") + "\n" + e.getMessage());
            return null;
        }
    }

    private void outputRoles() {
        String[] roles;
        try {
            roles = userAdm.getUserRoles();
        } catch (IUserAdministration.DataAccessException e) {
            ui.printError(Lang.msg("errorFetchRoles") + e.getMessage());
            return;
        }

        ui.showRoles(roles);
    }
}
