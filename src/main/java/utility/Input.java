package utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Integer getIntegerNum() {
        Integer num = -1;
        try {
            num = SCANNER.nextInt();
            SCANNER.nextLine();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return num;
    }

    public static Long getLongNum() {
        Long num = null;
        try {
            num = SCANNER.nextLong();
            SCANNER.nextLine();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static Double getDoubleNum() {
        Double num = null;
        try {
            num = SCANNER.nextDouble();
            SCANNER.nextLine();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static String getString() {
        String string = null;
        while (true) {
            try {
                string = SCANNER.nextLine();
                if (string.isBlank() || string.isEmpty()) {
                    System.out.println("Input can't empty");
                } else break;
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
        return string;
    }

    public static LocalTime getLocalTime() {
        boolean flag = true;
        Integer hour = null;
        Integer minutes = null;
        while (flag) {
            System.out.println("Enter hour: ");
            hour = getIntegerNum();
            System.out.println("Enter minutes: ");
            minutes = getIntegerNum();
            if (hour >= 0 && hour < 24) {
                if (minutes >= 0 && minutes < 60) {
                    flag = false;
                } else System.out.println("Wrong input for minutes. (try again)");
            } else System.out.println("Wrong input for hour. (try again)");
        }
        return LocalTime.of(hour, minutes);
    }

    public static LocalDate getLocalDate() {
        boolean flag = true;
        Integer year = null;
        Integer month = null;
        Integer day = null;
        while (flag) {
            System.out.println("Enter year:");
            year = getIntegerNum();
            System.out.println("Enter month:");
            month = getIntegerNum();
            System.out.println("Enter day:");
            day = getIntegerNum();
            if (month > 0 && month < 13) {
                if (month <= 6) {
                    if (day > 0 && day < 32) {
                        flag = false;
                    } else System.out.println("days of month in six first of year is 31");
                } else if (month >= 7 && month < 12) {
                    if (day > 0 && day < 31) {
                        flag = false;
                    } else System.out.println("days of month between 7-11 of year is 30");
                } else {
                    if (day > 0 && day < 30) {
                        flag = false;
                    } else System.out.println("days of Last month of year is 29");
                }
            } else System.out.println("Month should be between 1-12.");
        }
        return LocalDate.of(year, month, day);
    }
}
