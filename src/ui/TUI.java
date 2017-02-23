package ui;

import java.util.Scanner;

/**
 * Created by Kasper on 14-02-2017.
 */
public class TUI implements UI {
    private Scanner scanner;

    public TUI() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String getMenuChoice(String... menuOptions) {
        int choice;

        while (true) {
            for (int i = 0; i < menuOptions.length; i++) {
                System.out.println(i + 1 + " - " + menuOptions[i]);
            }

            try {
                choice = Integer.parseInt(getUserInput(lang.Lang.msg("selectNumber")));
                System.out.println();
                return menuOptions[choice - 1];
            } catch (IndexOutOfBoundsException e) {
                System.out.println(lang.Lang.msg("selectMenuItem"));
            } catch (Exception e) {
                System.out.println("haveToTypeNumber");
                System.out.println(lang.Lang.msg("haveToTypeNumber") + "!\n");
            }
        }
    }

    @Override
    public void printMsg(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String message) {
        System.err.println(message);
    }

    @Override
    public String getUserInput(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    @Override
    public void showRoles(String[] roles) {
        System.out.print("|");
        for (String role : roles) {
            System.out.print(" " + role + " |");
        }
        System.out.println();
    }
}
