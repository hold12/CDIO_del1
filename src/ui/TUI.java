package ui;

import dal.IUserAdministration;
import dal.UserAdministrationDB;

import java.util.Scanner;

/**
 * Created by Kasper on 14-02-2017.
 */
public class TUI implements UI
{
    private Scanner scanner;

    public TUI()
    {
        scanner = new Scanner(System.in);
    }

    @Override
    public String getMenuChoice(String... menuOptions) {
        int choice;

        while(true) {
            for (int i = 0; i < menuOptions.length; i++) {
                System.out.println(i + 1 + " - " + menuOptions[i]);
            }

            try {
                choice = Integer.parseInt(getUserInput("Select number"));
                System.out.println();
                return menuOptions[choice-1];
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Selection out of bounds. Select one of the above menu items.");
            } catch (Exception e) {
                System.out.println("You have to type a number!\n");
            }
        }
    }

    @Override
    public void printMsg(String message)
    {
        System.out.println(message);
    }

    @Override
    public String getUserInput(String message)
    {
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

    //    private String createNumberedList(String[] list)
//    {
//        String newList = "";
//        int count = 1;
//
//        for (String element : list)
//        {
//            newList += count + " - " + element + "\n";
//        }
//
//        return newList;
//    }
//
//    private String createNumberedList(String[] list, String messageBeforeList)
//    {
//        String newList = "";
//
//        newList += messageBeforeList + "\n";
//        newList += createNumberedList(list);
//
//        return newList;
//    }
//    }

//    @Override

    //    @Override
//    public void printMainMenu() throws IOException
//    {
//        clearConsole();
//        String[] optionList = {"Create user", "Show users", "Edit user", "Delete user", "Exit program"};
//        System.out.print(createNumberedList(optionList , "Choose a following option by typing the number"));

//    public void listUsers(List<User> listOfUser)
//    {
//        for (User user : listOfUser)
//        {
//            System.out.println(user.toString());
//        }
//    }



//    @Override
//    public User createUser()
//    {
//        int userId;
//        String userName;
//        String initials;
//        String cpr;
//        String[] tempRoles;
//        List<String> roles = new ArrayList<>();
//
//        userId = Integer.parseInt(getUserInput("Enter user id"));
//        userName = getUserInput("Enter username");
//        initials = getUserInput("Enter initials");
//        tempRoles = getUserInput("Enter roles, separated by comma").split(",");
//        cpr = getUserInput("Enter cpr");
//        roles = Arrays.asList(tempRoles);
//
//        return new User(userId, userName, initials, roles, cpr);
//    }

//    @Override
//    public User editUser(User user)
//    {
//        String input;
//        String username = user.getUserName();
//        String initials = user.getInitials();
//        Password password = user.getPassword();
//        String[] optionList = {"Username", "Initials", "Roles", "Password", "Exit"};
//        List<String> roles = user.getRoles();
//
//        do
//        {
//            System.out.print(createNumberedList(optionList ,"Choose what you want to edit"));
//
//            input = scanner.nextLine();
//
//            switch(input)
//            {
//                case "1":
//                    username = getUserInput("Enter new username");
//                    break;
//                case "2":
//                    initials = getUserInput("Enter new initials");
//                    break;
//                case "3":
//                    String[] newRoles = getUserInput("Enter new roles, separate with comma").split(",");
//                    roles = Arrays.asList(newRoles);
//                    break;
//                case "4":
//                    password.setPassword(getUserInput("Enter new password"));
//                    break;
//            }
//        }
//        while (!input.equals(String.valueOf(optionList.length))); //Converting int to string, to compare
//
//        return new User(user.getUserId(), username, initials ,roles, user.getCpr(), password);
//    }
//
//    @Override
//    public void removedUserMsg(User user)
//    {
//        System.out.println("This user was successfully removed: \n" + user.toString());
//    }
//
//    // TODO: Remove, it does not work :'(
//    private void clearConsole() throws IOException
//    {
//        final String operatingSystem = System.getProperty("os.name");
//
//        if (operatingSystem .contains("Windows"))
//            Runtime.getRuntime().exec("cls");
//        else
//            Runtime.getRuntime().exec("clear");
//    }



    //This is trash...

//    private List<String> defineRoles()
//    {
//        List<String> roles = new ArrayList<>();
//
//        System.out.println();
//        System.out.print("User Roles \n" +
//                "Must be one of the following \n" +
//                "Admin \n" +
//                "Pharmacist \n" +
//                "Foreman \n" +
//                "Operator \n" +
//                "Type user roles (Separated by comma): ");
//
//        while(!roles.isEmpty())
//        {
//            for (String tempString : scanner.nextLine().split(","))
//            {
//                if (tempString.equals("Admin") ||
//                        tempString.equals("Pharmacist") ||
//                        tempString.equals("Foreman") ||
//                        tempString.equals("Operator"))
//                {
//                    System.out.println("Roles does not meet the requirements! Try again");
//                    roles.clear();
//                    break;
//                }
//                else
//                {
//                    roles.add(tempString);
//                }
//            }
//        }
//
//        return roles;
//    }
//
//    private String defineUsername()
//    {
//        int userNameMaxLen = 20;
//        int userNameMinLen = 2;
//        String input;
//        String userName = "";
//
//        System.out.println("Please type following information about the user");
//        System.out.print("Username (Between 2 and 20 characters): ");
//
//        do
//        {
//            input = scanner.nextLine();
//
//            if (input.length() > userNameMinLen && input.length() < userNameMaxLen)
//                userName = input;
//            else
//                System.out.println("Username does not meet requirements! Try again");
//        }
//        while (input.length() > userNameMinLen && input.length() < userNameMaxLen);
//
//        return userName;
//    }
//
//    private String defineInitials()
//    {
//        int userInitialsMaxLen = 4;
//        int userInitialsMinLen = 2;
//        String input;
//        String initials = "";
//
//        System.out.println();
//        System.out.print("User Initials (Between 2 and 4 characters): ");
//
//        do
//        {
//            input = scanner.nextLine();
//
//            if (input.length() > userInitialsMinLen && input.length() < userInitialsMaxLen)
//            {
//                initials = input;
//            }
//            else
//                System.out.println("Initials does not meet requirements! Try again");
//        }
//        while(input.length() > userInitialsMinLen && input.length() < userInitialsMaxLen);
//
//        return initials;
//    }
//
//    private String defineCpr()
//    {
//        int userCprLen = 10;
//        String input;
//        String cpr = "";
//
//        System.out.println();
//        System.out.print("User Cpr (Must be 10 characters): ");
//
//        do
//        {
//            input = scanner.nextLine();
//
//            if(input.length() == 10)
//                cpr = input;
//            else
//                System.out.println("Cpr does not meet the requirements! Try again");
//        }
//        while(input.length() == userCprLen);
//
//        return cpr;
//    }
}
