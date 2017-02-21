package ui;

import dto.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrote by Kasper on 14-02-2017.
 */
public interface UI
{
    String getMenuChoice(String... menuOptions);
    void printMainMenu() throws IOException;
    void printMsg(String message);
    void listUsers(List<User> listOfUsers);
    void removedUserMsg(User user);
    String getUserInput(String message);
    User editUser(User user);
    User createUser();
}
