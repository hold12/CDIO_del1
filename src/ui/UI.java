package ui;

/**
 * Wrote by Kasper on 14-02-2017.
 */
public interface UI
{
    String getMenuChoice(String... menuOptions);
    String getUserInput(String message);
    void printMsg(String message);
    void showRoles(String[] roles);
}
