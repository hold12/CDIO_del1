package controller;

import dto.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by freya on 21-02-2017.
 */
public class UserValidator {
    public static boolean isCprValid(String cpr) {
        if (cpr.length() != 10) return false;
        String date = cpr.substring(0, 6); //the first six digits of cpr

        return (cpr.length() == 10 && isDateValid(date));
    }

    private static boolean isDateValid(String date) {
        SimpleDateFormat df = new SimpleDateFormat("ddMMyy");
        df.setLenient(false); //by doing this the parsing is more "strict"

        try {
            df.parse(date); //we try to parse the first six digits as a date
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isInitialsValid(String initials) {
        return initials.length() <= 4 && initials.length() >= 2;
    }

    public static boolean isUsernameValid(String username) {
        return username.length() <= 20 && username.length() >= 2 && containsAtLeastOneNonNumericCharacter(username);
    }

    public static String[] removeDuplicateRoles(User user, String[] selectedRoles) {
        List<String> selectedLower = new ArrayList<>();
        List<String> userRolesLower = new ArrayList<>();

        for (String role : selectedRoles) {
            selectedLower.add(role.toLowerCase());
        }

        for (int i = 0; i < user.getRoles().size(); i++) {
            userRolesLower.add(user.getRoles().get(i).toLowerCase());
        }

        for (int i = 0; i < selectedLower.size(); i++) {
            for (String userRole : userRolesLower) {
                if (selectedLower.get(i).equals(userRole))
                    selectedLower.remove(i);
            }
        }

        return selectedLower.stream().toArray(String[]::new);
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
