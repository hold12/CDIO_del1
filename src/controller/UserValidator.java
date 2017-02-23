package controller;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by freya on 21-02-2017.
 */
public class UserValidator {
    public static boolean isCprValid(String cpr){
        if (cpr.length() != 10) return false;
        String date = cpr.substring(0,6); //the first six digits of cpr

        if(cpr.length() == 10 && isDateValid(date)) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean isDateValid(String date){
        SimpleDateFormat df = new SimpleDateFormat("ddMMyy");
        df.setLenient(false); //by doing this the parsing is more "strict"

        try{
            df.parse(date); //we try to parse the first six digits as a date
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }

    public static boolean isInitialsValid(String initials) {
        return initials.length() <= 4 && initials.length() >= 2;
    }

    public static boolean isUsernameValid(String username) {
            return username.length() <= 20 && username.length() >= 2 && containsAtLeastOneNonNumericCharacter(username);
    }

    private static boolean containsAtLeastOneNonNumericCharacter(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
