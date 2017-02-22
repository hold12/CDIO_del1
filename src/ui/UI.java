package ui;

import dal.UserAdministrationDB;
import dto.User;

import java.io.IOException;
import java.util.List;

/**
 * Wrote by Kasper on 14-02-2017.
 */
public interface UI
{
    String getMenuChoice(String... menuOptions);
    String getUserInput(String message);
    void printMsg(String message);
    void showRoles(String[] roles);

    // TODO: Methods below should be removed.
//    void printMainMenu() throws IOException;
//    void listUsers(List<User> listOfUsers);
//    void removedUserMsg(User user);
//    User editUser(User user);
//    User createUser();
}
