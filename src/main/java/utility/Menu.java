package utility;

import model.*;
import service.spouseService.SpouseService;
import service.studentService.StudentService;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    final StudentService studentService = ApplicationContext.getStudentServices();
    final SpouseService spouseService = ApplicationContext.getSpouseService();
    LoanMenu loanMenu = new LoanMenu();

    public void mainMenu() {
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("*******************************************");
            System.out.println("** Welcome to the Student Loan Payment System **");
            System.out.println("*******************************************");
            System.out.println("1-Sign in ");
            System.out.println("2-register student ");
            System.out.println("0-Exit");
            System.out.print("Please select an option: ");
            System.out.println();

            try {
                System.out.println("Enter a number:");
                numberInput = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }

            switch (numberInput) {
                case 1 -> {
                    String nationalId = studentSignIn();
                    loanMenu.loanMainMenu(nationalId);
                }
                case 2 -> studentSignUp();
                case 0 -> System.out.println(" ** Bye :) **");
                default -> System.out.println("Wrong input");
            }
        }
    }

    private void studentSignUp() {
        System.out.println("** Register Yourself **");
        System.out.println("Please enter firstname :");
        String name = Input.getString();

        System.out.println("Please enter lastname :");
        String lastname = Input.getString();

        System.out.println("Please enter father name :");
        String fatherName = Input.getString();

        System.out.println("Please enter mother name :");
        String motherName = Input.getString();

        String nationalId = getValidNationalId();

        System.out.println("complete  birthdate info ");
        LocalDate birthdate = Input.getLocalDate();

        System.out.println("Please enter your student code :");
        String studentCode = Input.getString();

        System.out.println("Please enter your university name :");
        String universityName = Input.getString();

        TypeOfUniversity typeOfUniversity = universityTypeMenu();

        System.out.println("Please enter your Entry date :");
        LocalDate entryDate = Input.getLocalDate();

        Section sectionStudy = sectionStudyMenu();

        Boolean dormitory = dormitoryMenu();

        Status status = statuMenu();


        Student student = Student
                .builder()
                .firstName(name)
                .lastName(lastname)
                .fatherName(fatherName)
                .motherName(motherName)
                .nationalId(nationalId)
                .birthDate(birthdate)
                .studentNumber(studentCode)
                .universityName(universityName)
                .typeUniversity(typeOfUniversity)
                .entranceYear(entryDate)
                .section(sectionStudy)
                .dormitory(dormitory)
                .status(status)
                .build();
        studentService.saveOrUpdate(student);

        student.setPassword();
        studentService.saveOrUpdate(student);
        System.out.println("username : " + nationalId );
        System.out.println("password : " + student.getPassword() );
    }


    private Status statuMenu() {
        Status status = null;
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("Are you single or Married");
            System.out.println("1-Single");
            System.out.println("2-Married");
            System.out.println("0-Exit");
            System.out.println();

            try {
                System.out.println("Enter a number:");
                numberInput = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }

            switch (numberInput) {
                case 1 -> {
                    status = Status.SINGLE;
                    return status;
                }
                case 2 -> {
                    status = Status.MARRIED;
                    return status;
                }
                case 0 -> System.out.println();
                default -> System.out.println("Wrong input");
            }
        }
        return status;
    }

    private Boolean dormitoryMenu() {
        Boolean dormitory = null;
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("Are you at dormitory ?");
            System.out.println("1-Yes");
            System.out.println("2-No");
            System.out.println("0-Exit");
            System.out.println();

            try {
                System.out.println("Enter a number:");
                numberInput = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }

            switch (numberInput) {
                case 1 -> {
                    dormitory = true;
                    return dormitory;
                }
                case 2 -> {
                    dormitory = false;
                    return dormitory;
                }
                case 0 -> System.out.println();
                default -> System.out.println("Wrong input");
            }
        }
        return dormitory;
    }

    public Section sectionStudyMenu() {
        Section sectionStudy = null;
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("*** Section Study Menu ***");
            System.out.println("Enter type of your Section Study");
            System.out.println("1-KARDANI ");
            System.out.println("2-KARSHENASI PEYVASTE ");
            System.out.println("3-KARSHENASI NAPEYVASTE ");
            System.out.println("4-KARSHENASI ARSHAD PEYVASTEH ");
            System.out.println("5-KARSHENASI ARSHAD NAPEYVASTEH ");
            System.out.println("6-DOKTORAY HERFEII ");
            System.out.println("7-DOKTORAY PEYVASTEH ");
            System.out.println("8-DOKTORAY NAPEYVASTEH ");
            System.out.println("0-Exit");
            System.out.println();

            try {
                System.out.println("Enter a number:");
                numberInput = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }

            switch (numberInput) {
                case 1 -> {
                    sectionStudy = Section.KARDANI;
                    return sectionStudy;
                }
                case 2 -> {
                    sectionStudy = Section.KARSHENASI_PEYVASTE;
                    return sectionStudy;
                }
                case 3 -> {
                    sectionStudy = Section.KARSHENASI_NAPEYVASTE;
                    return sectionStudy;
                }
                case 4 -> {
                    sectionStudy = Section.KARSHENASI_ARSHAD_PEYVASTEH;
                    return sectionStudy;
                }
                case 5 -> {
                    sectionStudy = Section.KARSHENASI_ARSHAD_NAPEYVASTEH;
                    return sectionStudy;
                }
                case 6 -> {
                    sectionStudy = Section.DOKTORAY_HERFEII;
                    return sectionStudy;
                }
                case 7 -> {
                    sectionStudy = Section.DOKTORAY_PEYVASTEH;
                    return sectionStudy;
                }
                case 8 -> {
                    sectionStudy = Section.DOKTORAY_NAPEYVASTEH;
                    return sectionStudy;
                }
                case 0 -> System.out.println();
                default -> System.out.println("Wrong input");
            }
        }
        return sectionStudy;
    }

    public TypeOfUniversity universityTypeMenu() {
        TypeOfUniversity typeUniversity = null;
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("*** University Type Menu ***");
            System.out.println("Enter type of your University");
            System.out.println("1-SARASARY   --->   دولتی");
            System.out.println("2-SARASARY_SHABANE ---> دولتی شبانه");
            System.out.println("3-GHEYRENTEFAEE ---> غیرانتفاعی");
            System.out.println("4-PARDIS  --->   پردیس");
            System.out.println("5-ZARFIATMAZAD  --->   ظرفیت مازاد");
            System.out.println("6-PAYAMNOOR   --->  پیام نور");
            System.out.println("7-ELMIKARBORDI  --->   علمی کاربردی");
            System.out.println("8-AZAD  --->   آزاد اسلامی");
            System.out.println("0-Exit");
            System.out.println();

            try {
                System.out.println("Enter a number:");
                numberInput = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }

            switch (numberInput) {
                case 1 -> {
                    typeUniversity = TypeOfUniversity.SARASARY;
                    return typeUniversity;
                }

                case 2 -> {
                    typeUniversity = TypeOfUniversity.SARASARY_SHABANE;
                    return typeUniversity;
                }
                case 3 -> {
                    typeUniversity = TypeOfUniversity.GHEYRENTEFAEE;
                    return typeUniversity;
                }
                case 4 -> {
                    typeUniversity = TypeOfUniversity.PARDIS;
                    return typeUniversity;
                }
                case 5 -> {
                    typeUniversity = TypeOfUniversity.ZARFIATMAZAD;
                    return typeUniversity;
                }
                case 6 -> {
                    typeUniversity = TypeOfUniversity.PAYAMNOOR;
                    return typeUniversity;
                }
                case 7 -> {
                    typeUniversity = TypeOfUniversity.ELMIKARBORDI;
                    return typeUniversity;
                }
                case 8 -> {
                    typeUniversity = TypeOfUniversity.AZAD;
                    return typeUniversity;
                }
                case 0 -> System.out.println();
                default -> System.out.println("Wrong input");
            }
        }
        return typeUniversity;
    }

    private String studentSignIn() {
        String nationalId = "";
        String password = "";
        while (true) {
            System.out.println("Enter your national id :");
            nationalId = scanner.nextLine();
            System.out.println("Enter your password :");
            password = scanner.nextLine();

            List<Student> studentList = studentService.studentSignIn(nationalId, password);

            if (studentList != null) {
                String firstName = studentList.get(0).getFirstName();
                System.out.println("Welcome " + firstName);

                return nationalId;
            } else
                System.out.println("Username or password is invalid");
        }
    }

    public String getValidPassword() {
        String password;
        while (true) {
            System.out.println("Please enter your password :");
            password = scanner.nextLine();
            if (Validation.isPasswordValid(password))
                break;
            else
                System.out.println("This " + password + " is not strong , try again");
        }
        return password;
    }

    public String getValidNationalId() {
        String NationalId;
        while (true) {
            System.out.println("Please enter your National id :");
            NationalId = scanner.nextLine();
            if (Validation.validateMelliCode(NationalId))
                break;
            else
                System.out.println("This " + NationalId + " is not valid");
        }
        return NationalId;
    }
}