package controller;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by freya on 21-02-2017.
 */
public class CprValidation {

    public static boolean isCprValid(String cpr){
        if (cpr.length() == 0) return false;
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

}
