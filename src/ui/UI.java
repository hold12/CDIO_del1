package ui;

import dto.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrote by Kasper on 14-02-2017.
 */
public interface UI
{
    void printMainMenu();
    void printMsg(String message);
    void listUsers(List<User> listOfUsers);
    void closeProgram();
    void removedUserMsg(User user);
    String getUserInput();
    User editUser(User user);
    User createUser();
}
