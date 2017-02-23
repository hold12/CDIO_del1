package dto;

import java.util.Random;

/**
 * Created by freya on 17-02-2017.
 * Password generation code taken from:
 * http://theopentutorials.com/tutorials/java/util/generating-a-random-password-with-restriction-in-java/
 */
public class Password {
    private final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private final String NUM = "0123456789";
    private final String SPECIAL_CHARS = ".-_+!?=";
    private String password;

    public Password() {
        this.password = new String(generatePassword(8, 20, 1, 1, 1));
    }

    public Password(String password){
        this.password = password;
    }

    private char[] generatePassword(int minLen, int maxLen, int noOfCAPSAlpha,
                                    int noOfDigits, int noOfSplChars) {
        if (minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen)
            throw new IllegalArgumentException
                    ("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        int index;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPECIAL_CHARS.charAt(rnd.nextInt(SPECIAL_CHARS.length()));
        }
        for (int i = 0; i < len; i++) {
            if (pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }

    private int getNextIndex(Random rnd, int len, char[] pswd) {
        int index;
        while (pswd[index = rnd.nextInt(len)] != 0) ;
        return index;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password1 = (Password) o;

        return password.equals(password1.password);
    }
}
