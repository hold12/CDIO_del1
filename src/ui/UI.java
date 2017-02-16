package ui;

import dto.User;

/**
 * Created by freya on 14-02-2017.
 */
public interface UI
{
    void printWelcomeMsg();
    void printMsg(String message);
    void listUsers();
    void closeProgram();
    String getUserInput();
    User createUser();
    User editUser(User user);
    boolean removeUser(User user);
}
