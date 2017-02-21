package controller;

import dal.DBConnection;
import dal.IUserAdministration;
import dal.UserAdministrationDB;
import dto.User;
import ui.UI;

import java.util.ArrayList;
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

        if (userChoice.equals(menuOptions[0])) {
            try {
                userAdm.createUser(ui.createUser());
            } catch (IUserAdministration.DataAccessException e) {

            }
        } else if (userChoice.equals(menuOptions[1])) {
            List<User> users = new ArrayList<>();
            try {
                users = userAdm.getUserList();
            } catch (IUserAdministration.DataAccessException e) {

            }

            for (User user : users)
                System.out.println(user);
        }
    }
}
