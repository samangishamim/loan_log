package utility;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final Pattern PASSWORD_PATTERN ;
    static {
        PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])" +
                "(?=.*[0-9])(?=.*[@#!%&*])[A-Za-z0-9@#!%&*]{8,10}$") ;
    }
    public static boolean isPasswordValid(String password){
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }

    private static final Pattern EMAIL_PATTERN ;
    static {
        EMAIL_PATTERN = Pattern.compile("^(.+)@(\\S+)$");
    }
    public static boolean isEmailValid(String email){
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    private static final Pattern PHONE_PATTERN;
    static {
        PHONE_PATTERN = Pattern.compile("(0|\\+98)?([ ]|-|[()]){0,2}9[1|2|3|4]([ ]|-|[()]){0,2}(?:[0-9]([ ]|-|[()]){0,2}){8}");
    }
    public static boolean isNumberValid(String phone){
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }
    private static final Pattern WEBSITE_PATTERN;
    static {
        WEBSITE_PATTERN = Pattern.compile("((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)");
    }
    public static boolean isWebsiteValid(String website){
        Matcher matcher = WEBSITE_PATTERN.matcher(website);
        return matcher.matches();
    }

    public static boolean validateMelliCode(String melliCode) {

        String[] identicalDigits = {"0000000000", "1111111111", "2222222222", "3333333333",
                "4444444444", "5555555555", "6666666666", "7777777777", "8888888888", "9999999999"};

        if (melliCode.trim().isEmpty()) {
            System.out.println("National Code is empty");
            return false;
        } else if (melliCode.length() != 10) {
            System.out.println("National Code must be exactly 10 digits");
            return false;
        } else if (Arrays.asList(identicalDigits).contains(melliCode)) {
            System.out.println("MelliCode is not valid (Fake MelliCode)");
            return false;
        } else {
            int sum = 0;

            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(melliCode.charAt(i)) * (10 - i);
            }

            int lastDigit;
            int divideRemaining = sum % 11;

            if (divideRemaining < 2) {
                lastDigit = divideRemaining;
            } else {
                lastDigit = 11 - (divideRemaining);
            }

            if (Character.getNumericValue(melliCode.charAt(9)) == lastDigit) {
                System.out.println("MelliCode is valid");
                return true;
            } else {
                System.out.println("MelliCode is not valid");
                return false;
            }
        }
    }
}
