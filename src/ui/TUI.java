package ui;

import dto.User;

import java.util.ArrayList;

import java.util.Scanner;

/**
 * Created by freya on 14-02-2017.
 */
public class TUI implements UI
{
    Scanner scanner = new Scanner(System.in);

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
    public void listUsers(ArrayList<User> listOfUser)
    {
        for (int i = 0; i < listOfUser.size(); i++)
        {
            System.out.println(listOfUser.get(i).toString());
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
        int userNameMaxLen = 20;
        int userNameMinLen = 2;
        int userInitialsMaxLen = 4;
        int userInitialsMinLen = 2;
        int userCprLen = 10;
        String input;
        String userName = null;
        String initials = null;
        String cpr = null;
        ArrayList<String> roles = new ArrayList<>();

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
            String[] tempString = scanner.nextLine().split(",");

            for (int i = 0; i < tempString.length; i++)
            {
                if (tempString[i] != "Admin" ||
                        tempString[i] != "Pharmacist" ||
                        tempString[i] != "Foreman" ||
                        tempString[i] != "Operator")
                {
                    System.out.println("Roles does not meet the requirements! Try again");
                    roles.clear();
                    break;
                }
                else
                {
                    roles.add(tempString[i]);
                }
            }
        }

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

        return new User(-1, userName, initials, roles, cpr, null);
    }

    @Override
    public User editUser(User user)
    {
        //TODO finish writing method!
        String input;
        String username;

        do
        {
            System.out.println("Choose what you want to edit \n" +
                    "1 - Username \n" +
                    "2 - Initials \n" +
                    "3 - Roles \n" +
                    "4 - Cpr");

            input = scanner.nextLine();
        }
        while (!input.equals("Exit"));

        return new User(-1, null, null, null, null, null);
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
}
