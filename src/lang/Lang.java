package lang;/*
           ,                                             |
          /#\         _         _     _    ___   ___     | Made by hold 11 - 2016
         /###\       | |__   _ | | __| |  /_  | /_  |    | Version: v0.1.1
        /#####\      | '_ \ / \| |/ _  |    | |   | |    |
       /##,-,##\     | | | | O | | ( | |   _| |_ _| |_   | Anders Wiberg Olsen (s165241), Valentin Leon Christensen (s152735),
      /##(   )##\    |_| |_|\_/|_|\__,_|  |_____|_____|  | Iman Chelhi (s165228), Troels Just Christoffersen (s120052),
     /#.--   --.#\                                       | Sebastian Tibor Bakonyvári (s145918)
    /`           ´\                                      |
*/

import java.util.Locale;
import java.util.ResourceBundle;

public class Lang {
    private static String lang;
    private static String country;
    @SuppressWarnings("FieldCanBeLocal")
    private static Locale locale;

    private static ResourceBundle resourceBundle;

    public static String msg(String message) { return resourceBundle.getString(message); }

    public static void setLanguage(String[] args) {
        if (args.length != 2) {
            lang = "da";
            country = "DK";
        } else if (args.length == 2){
            lang = args[0];
            country = args[1];
        } else {
            System.err.println("Error.");
            System.out.println("Usage: java -jar <app_name>.jar <language> <country>");
        }

        try
        {
            locale = new Locale(lang, country);
            resourceBundle = ResourceBundle.getBundle("lang", locale);
        }
        catch (Exception ex)
        {
            System.err.println("You either specified some wrong arguments or you specified a language that doesn't exist in this game.");
            System.err.println("Usage: java -jar game.jar <language> <country> (ex. da DK)");
        }

    }
}