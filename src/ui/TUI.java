package ui;

import dto.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Wrote by Kasper on 14-02-2017.
 */
public class TUI implements UI
{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void printMainMenu()
    {
        System.out.println("Choose a following option by typing the number \n" +
                "1 - Create User \n" +
                "2 - Show Users \n" +
                "3 - Edit User \n" +
                "4 - Delete User \n" +
                "5 - Exit program");
    }

    @Override
    public void printMsg(String message)
    {
        System.out.println(message);
    }

    @Override
    public void listUsers(List<User> listOfUser)
    {
        for (User user : listOfUser)
        {
            System.out.println(user.toString());
        }
    }

    @Override
    public void closeProgram()
    {
        System.exit(0);
    }

    @Override
    public String getUserInput()
    {
        return scanner.nextLine();
    }

    @Override
    public User createUser()
    {
        String userName;
        String initials;
        String cpr;
        List<String> roles;

        userName = defineUsername();
        initials = defineInitials();
        roles = defineRoles();
        cpr = defineCpr();

        return new User(-1, userName, initials, roles, cpr);
    }

    @Override
    public User editUser(User user)
    {
        String input;
        String username = user.getUserName();
        String initials = user.getInitials();
        List<String> roles = user.getRoles();

        do
        {
            System.out.println("Choose what you want to edit \n" +
                    "1 - Username \n" +
                    "2 - Initials \n" +
                    "3 - Roles \n" +
                    "4 - Exit");

            input = scanner.nextLine();

            switch(input)
            {
                case "1":
                    username = defineUsername();
                    break;
                case "2":
                    initials = defineInitials();
                    break;
                case "3":
                    roles = defineRoles();
                    break;
            }
        }
        while (!input.equals("4"));

        return new User(user.getUserId(), username, initials ,roles, user.getCpr());
    }

    @Override
    public void removedUserMsg(User user)
    {
        System.out.println("This user was successfully removed: \n" +
                user.getUserId() + "\n" +
                user.getUserName() + "\n" +
                user.getInitials() + "\n" +
                user.getCpr());
    }

    public void clearConsole() throws IOException
    {
        final String operatingSystem = System.getProperty("os.name");

        if (operatingSystem .contains("Windows"))
        {
            Runtime.getRuntime().exec("cls");
        }
        else
        {
            Runtime.getRuntime().exec("clear");
        }
    }

    private List<String> defineRoles()
    {
        List<String> roles = new ArrayList<>();

        System.out.println();
        System.out.print("User Roles \n" +
                "Must be one of the following \n" +
                "Admin \n" +
                "Pharmacist \n" +
                "Foreman \n" +
                "Operator \n" +
                "Type user roles (Separated by comma): ");

        while(!roles.isEmpty())
        {
            for (String tempString : scanner.nextLine().split(","))
            {
                if (tempString.equals("Admin") ||
                        tempString.equals("Pharmacist") ||
                        tempString.equals("Foreman") ||
                        tempString.equals("Operator"))
                {
                    System.out.println("Roles does not meet the requirements! Try again");
                    roles.clear();
                    break;
                }
                else
                {
                    roles.add(tempString);
                }
            }
        }

        return roles;
    }

    private String defineUsername()
    {
        int userNameMaxLen = 20;
        int userNameMinLen = 2;
        String input;
        String userName = "";

        System.out.println("Please type following information about the user");
        System.out.print("Username (Between 2 and 20 characters): ");

        do
        {
            input = scanner.nextLine();

            if (input.length() > userNameMinLen && input.length() < userNameMaxLen)
                userName = input;
            else
                System.out.println("Username does not meet requirements! Try again");
        }
        while (input.length() > userNameMinLen && input.length() < userNameMaxLen);

        return userName;
    }

    private String defineInitials()
    {
        int userInitialsMaxLen = 4;
        int userInitialsMinLen = 2;
        String input;
        String initials = "";

        System.out.println();
        System.out.print("User Initials (Between 2 and 4 characters): ");

        do
        {
            input = scanner.nextLine();

            if (input.length() > userInitialsMinLen && input.length() < userInitialsMaxLen)
            {
                initials = input;
            }
            else
                System.out.println("Initials does not meet requirements! Try again");
        }
        while(input.length() > userInitialsMinLen && input.length() < userInitialsMaxLen);

        return initials;
    }

    private String defineCpr()
    {
        int userCprLen = 10;
        String input;
        String cpr = "";

        System.out.println();
        System.out.print("User Cpr (Must be 10 characters): ");

        do
        {
            input = scanner.nextLine();

            if(input.length() == 10)
                cpr = input;
            else
                System.out.println("Cpr does not meet the requirements! Try again");
        }
        while(input.length() == userCprLen);

        return cpr;
    }
}
