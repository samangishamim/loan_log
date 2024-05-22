package utility;

import model.*;
import service.addressService.AddressService;
import service.bankAccountService.BankAccountService;
import service.installmentService.InstallmentService;
import service.loanService.LoanService;
import service.spouseService.SpouseService;
import service.studentService.StudentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class LoanMenu {
    Scanner scanner = new Scanner(System.in);
    final StudentService studentService = ApplicationContext.getStudentServices();
    final SpouseService spouseService = ApplicationContext.getSpouseService();
    final AddressService addressService = ApplicationContext.getAddressService();
    final BankAccountService bankAccountService = ApplicationContext.getBankAccountService();
    final InstallmentService installmentService = ApplicationContext.getInstallmentService();
    final LoanService loanService = ApplicationContext.getLoanService();
    RefundMenu refundMenu = new RefundMenu();

    public void loanMainMenu(String nationalId) {
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("*** Loan Main Menu ***");
            System.out.println("1-Choose loan ---> ثبت نام برای وام");
            System.out.println("2-See  payments ----> بازپرداخت");
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
                case 1 -> checkDateAndThenLoanTypeMenu(nationalId);
                case 2 -> refundMenu.refundMainMenu(nationalId);
                case 0 -> System.out.println();
                default -> System.out.println("Wrong input");
            }
        }
    }

    private void checkDateAndThenLoanTypeMenu(String nationalId) {
        LocalDate localDate = getTodayDate();
        LocalDate startDate = LocalDate.of(2024, 10, 21);
        LocalDate endDate = LocalDate.of(2024, 10, 29);

        boolean before = localDate.isBefore(endDate);
        boolean after = localDate.isAfter(startDate);
        if (after && before) {
            loanTypeMenu(nationalId);
        } else {
            System.out.println("You cant choose in this date");
            loanMainMenu(nationalId);
        }
    }

    private LocalDate getTodayDate() {
        System.out.println("enter this year : ");
        int thisYear = Integer.parseInt(scanner.nextLine());
        System.out.println("enter this month : ");
        int thisMonth = Integer.parseInt(scanner.nextLine());
        System.out.println("enter day : ");
        int thisDay = Integer.parseInt(scanner.nextLine());
        return LocalDate.of(thisYear, thisMonth, thisDay);
    }

    public void loanTypeMenu(String nationalId) {
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("*** Choose Loan Menu ***");
            System.out.println("Which type of loan you want ?");
            System.out.println("1-VAM SHAHRIE  --->  وام شهریه");
            System.out.println("2-VAM TAHSILI  --->  وام تحصیلی");
            System.out.println("3-VAM VADEEMASKAN --->  وام ودیعه مسکن");
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
                case 1 -> vamShahrie(nationalId);
                case 2 -> vamTahsili(nationalId);
                case 3 -> vamVadeeMaskan(nationalId);
                case 0 -> System.out.println();
                default -> System.out.println("Wrong input");
            }
        }
    }

    public void vamVadeeMaskan(String nationalId) {

        List<Student> studentList = studentService.studentInfo(nationalId);
        Student getStudent;
        if (studentList != null) {
            getStudent = studentList.get(0);
        } else {
            System.out.println("student not found");
            return;
        }

        boolean marriedFlag = getStudent.isMarried();
        boolean housingFlag = !getStudent.getDormitory();
        if (!(marriedFlag && housingFlag)) {
            System.out.println("you'r not qualified for getting the maskan loan ");
            return;
        }

        System.out.println("enter your spouse national id : ");
        String spouseNationalId = scanner.nextLine();
        List<Student> studentList2 = studentService.studentInfo(spouseNationalId);
        Long spouseId = null;
        if (studentList2.size() != 0) {
            spouseId = studentList2.get(0).getId();
            List<Loan> loanList = loanService.findMaskanLoanByStudentId(spouseId);

            if (loanList != null) {
                System.out.println("maskan Loan is registered by your spouse ");
                return;
            }
        }
        System.out.println("enter your spouse firstname : ");
        String spouseFirstName = scanner.nextLine();
        System.out.println("enetr your spouse lastname :");
        String spouseLastName = scanner.nextLine();

        Spouse spouse = Spouse.builder()
                .spouseFirstName(spouseFirstName)
                .spouseLastName(spouseLastName)
                .spouseNationalId(spouseNationalId)
                .build();

        spouseService.saveOrUpdate(spouse);
        loanStatusvamVadeeMaskan(getStudent);

    }

    private void loanStatusvamVadeeMaskan(Student getStudent) {
        getAddress(getStudent);

        Optional<Address> studentAddress = addressService.getStudentAddress(getStudent);
        Province province = studentAddress.get().getProvince();

        System.out.println("Enter your semester : ");
        Integer semester = Input.getIntegerNum();

        LocalDate now = LocalDate.now();

        double loanAmount = 0D;
        if (province.equals(Province.TEHRAN)) {
            loanAmount = 32000000;
        } else if (province.equals(Province.GUILAN) ||
                province.equals(Province.ESFAHAN) ||
                province.equals(Province.EAST_AZARBAIJAN) ||
                province.equals(Province.FARS) ||
                province.equals(Province.KHUZESTAN) ||
                province.equals(Province.QOM) ||
                province.equals(Province.KHORASAN_RAZAVI) ||
                province.equals(Province.ALBORZ))
            if (getStudent.getStatus().equals(Status.MARRIED) &&
                    getStudent.getDormitory().equals(false)) {
                loanAmount = 26000000;
            } else {
                System.out.println("You cant get this loan !!!");
                loanMainMenu(getStudent.getNationalId());
            }
        else {
            loanAmount = 19500000;
        }

        Optional<Loan> studentWithSemester = loanService.findStudentWithSemester(getStudent, semester);
        if (studentWithSemester.isPresent()) {
            System.out.println("You gave this loan before");
        } else
            approvevamVadeeMaskan(getStudent, semester, now, loanAmount);

    }

    private void approvevamVadeeMaskan(Student getStudent, Integer semester, LocalDate now, double loanAmount) {
        getBankAccountInfo(getStudent);

        LoanType vamShahrie = LoanType.HOUSING;


        Loan loan = Loan
                .builder()
                .loanType(vamShahrie)
                .semester(semester)
                .loanDate(now)
                .loanAmount(loanAmount)
                .student(getStudent)
                .build();
        loanService.saveOrUpdate(loan);

        int installmentNumber = 0;
        Double shouldPay = 0D;
        Double profit = (loanAmount * 4) / 100;
        shouldPay = loanAmount + profit;
        Double shouldPayFinal = shouldPay / 60;

        shouldPay = loanAmount * (1 + 0.04 * 5) / 372;


        int cof=0;
        LocalDate dueDate = getTodayDate();
        for (int i = 0; i < 60; i++) {
            installmentNumber = i + 1;
            Boolean isPayed = false;

            dueDate = dueDate.plusMonths(i);

//                LocalDate paymentDateTime = LocalDate.now();
            if (i % 12 == 0) {

                shouldPay=(++cof)*shouldPay;
            }

            Installment installment = Installment
                    .builder()
                    .installmentNumber(installmentNumber)
                    .isPayed(isPayed)
                    .dueDate(dueDate)
                    .loan(loan)
//                    .paymentDate(paymentDateTime)
                    .shouldPay(shouldPayFinal)
                    .build();
            installmentService.saveOrUpdate(installment);
        }
    }

    private void getAddress(Student getStudent) {
        System.out.println("You should Enter Your Address Information :");

        System.out.println("Please Enter Your Rental Number :");
        String rentalNumber = Input.getString();

        Province province = getProvince();

        System.out.println("Please Enter Your Street Address :");
        String streetAddress = Input.getString();

        System.out.println("Please Enter Your Postal Code :");
        String postalCode = Input.getString();

        Address address = Address
                .builder()
                .rentalAgreementNumber(rentalNumber)
                .province(province)
                .streetAddress(streetAddress)
                .postalCode(postalCode)
                .student(getStudent)
                .build();
        addressService.saveOrUpdate(address);
    }

    public void vamTahsili(String nationalId) {
        List<Student> studentList = studentService.studentInfo(nationalId);
        Student getStudent = null;
        if (studentList != null) {
            getStudent = studentList.get(0);
        } else {
            System.out.println("student not found");
            return;
        }
        loanStatusvamTahsili(getStudent);
    }

    private void loanStatusvamTahsili(Student getStudent) {
        System.out.println("Enter your semester : ");
        Integer semester = Input.getIntegerNum();

        Optional<Loan> studentWithSemester = loanService.findStudentWithSemester(getStudent, semester);

        if (studentWithSemester.isPresent()) {
            Integer getLoanSemester = studentWithSemester.get().getSemester();
            if (getLoanSemester.equals(semester)) {
                System.out.println("you cant choose loan because you have one loan in this semester");
                loanTypeMenu(getStudent.getNationalId());
            } else {
                System.out.println("You can continue");
                approvevamTahsili(getStudent, semester);
            }
        } else {
            System.out.println("You can continue");
            approvevamTahsili(getStudent, semester);
        }
    }

    private void approvevamTahsili(Student getStudent, Integer semester) {
        double loanAmount = 0D;
        if (getStudent.getSection().equals(Section.KARDANI) ||
                getStudent.getSection().equals(Section.KARSHENASI_PEYVASTE) ||
                getStudent.getSection().equals(Section.KARSHENASI_NAPEYVASTE)) {
            loanAmount = 1900000D;
        } else if (getStudent.getSection().equals(Section.KARSHENASI_ARSHAD_PEYVASTEH) ||
                getStudent.getSection().equals(Section.KARSHENASI_ARSHAD_NAPEYVASTEH) ||
                getStudent.getSection().equals(Section.DOKTORAY_HERFEII) ||
                getStudent.getSection().equals(Section.DOKTORAY_PEYVASTEH)) {
            loanAmount = 2250000D;
        } else
            loanAmount = 2600000D;

        LoanType vamShahrie = LoanType.EDUCATION;

        LocalDate now = LocalDate.now();

        getBankAccountInfo(getStudent);

        Loan loan = Loan
                .builder()
                .loanType(vamShahrie)
                .semester(semester)
                .loanDate(now)
                .loanAmount(loanAmount)
                .student(getStudent)
                .build();
        loanService.saveOrUpdate(loan);

        int installmentNumber = 0;
        Double shouldPay = 0D;
        Double profit = (loanAmount * 4) / 100;
        shouldPay = loanAmount + profit;
        Double shouldPayFinal = shouldPay / 60;

        shouldPay = loanAmount * (1 + 0.04 * 5) / 372;


        int cof=0;
        LocalDate dueDate = getTodayDate();
        for (int i = 0; i < 60; i++) {
            installmentNumber = i + 1;
            Boolean isPayed = false;

            dueDate = dueDate.plusMonths(i);

//                LocalDate paymentDateTime = LocalDate.now();

            if (i % 12 == 0) {

                shouldPay=(++cof)*shouldPay;
            }

            Installment installment = Installment
                    .builder()
                    .installmentNumber(installmentNumber)
                    .isPayed(isPayed)
                    .dueDate(dueDate)
                    .loan(loan)
//                    .paymentDate(paymentDateTime)
                    .shouldPay(shouldPayFinal)
                    .build();
            installmentService.saveOrUpdate(installment);
        }
    }

    public void vamShahrie(String nationalId) {
        List<Student> studentList = studentService.studentInfo(nationalId);
        Student getStudent = null;
        if (studentList != null) {
            getStudent = studentList.get(0);
        }
        loanStatusVamShahrie(getStudent);
    }

    private void loanStatusVamShahrie(Student getStudent) {
        System.out.println("Enter your semester : ");
        Integer semester = Input.getIntegerNum();

        Optional<Loan> studentWithSemester = loanService.findStudentWithSemester(getStudent, semester);

        if (studentWithSemester.isPresent()) {
            Integer getLoanSemester = studentWithSemester.get().getSemester();
            if (getLoanSemester.equals(semester)) {
                System.out.println("you cant choose loan because you have one loan in this semester");
                loanTypeMenu(getStudent.getNationalId());
            } else {
                System.out.println("You can continue");
                approveVamShahrie(getStudent, semester);
            }
        } else {
            System.out.println("You can continue");
            approveVamShahrie(getStudent, semester);
        }
    }

    private void approveVamShahrie(Student getStudent, Integer semester) {
        LocalDate now = LocalDate.now();

        double loanAmount = 0D;
        if (!getStudent.getTypeUniversity().equals(TypeOfUniversity.SARASARY)) {
            if (getStudent.getSection().equals(Section.KARDANI) ||
                    getStudent.getSection().equals(Section.KARSHENASI_PEYVASTE) ||
                    getStudent.getSection().equals(Section.KARSHENASI_NAPEYVASTE)) {
                loanAmount = 1300000D;
            } else if (getStudent.getSection().equals(Section.KARSHENASI_ARSHAD_PEYVASTEH) ||
                    getStudent.getSection().equals(Section.KARSHENASI_ARSHAD_NAPEYVASTEH) ||
                    getStudent.getSection().equals(Section.DOKTORAY_HERFEII) ||
                    getStudent.getSection().equals(Section.DOKTORAY_PEYVASTEH)) {
                loanAmount = 2600000D;
            } else
                loanAmount = 6500000D;

            LoanType vamShahrie = LoanType.TUITION;

            getBankAccountInfo(getStudent);

            Loan loan = Loan
                    .builder()
                    .loanType(vamShahrie)
                    .semester(semester)
                    .loanDate(now)
                    .loanAmount(loanAmount)
                    .student(getStudent)
                    .build();
            loanService.saveOrUpdate(loan);


            int installmentNumber = 0;
            Double shouldPay = 0D;
//            Double profit = (loanAmount * 4) / 100;
//            shouldPay = loanAmount + profit;
//            Double shouldPayFinal = shouldPay / 60;

            shouldPay = loanAmount * (1 + 0.04 * 5) / 372;


            int cof=0;
            LocalDate dueDate = getTodayDate();
            for (int i = 0; i < 60; i++) {
                installmentNumber = i + 1;
                Boolean isPayed = false;


                dueDate = dueDate.plusMonths(i);

//                LocalDate paymentDateTime = LocalDate.now();
                if (i % 12 == 0) {

                    shouldPay=(++cof)*shouldPay;
                }

                Installment installment = Installment
                        .builder()
                        .installmentNumber(installmentNumber)
                        .isPayed(isPayed)
                        .dueDate(dueDate)
                        .loan(loan)
//                        .paymentDate(paymentDateTime)
                        .shouldPay(shouldPay)
                        .build();
                installmentService.saveOrUpdate(installment);
            }
        } else {
            System.out.println("You cant get this loan because of type SARASARY !!!");
            loanTypeMenu(getStudent.getNationalId());
        }
    }

    private void getBankAccountInfo(Student getStudent) {
        System.out.println("** You should enter your bank acount information **");

        String cardNumber="";
        do {
            System.out.println("Enter your card number :");
             cardNumber = Input.getString();
        }while (!isBankCardNumberValid(cardNumber));


        System.out.println("Enter your cvv2 number :");
        Double cvv2 = Input.getDoubleNum();

        LocalDate expiration = Input.getLocalDate();

        BankAccount bankAccount = BankAccount
                .builder()
                .cardNumber(cardNumber)
                .cvv2(cvv2)
                .expirationDate(expiration)
                .student(getStudent)
                .build();
        bankAccountService.saveOrUpdate(bankAccount);
    }


    public Province getProvince() {
        Province[] values = Province.values();
        Province province = null;
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("*** Province Menu ***");
            System.out.println("Enter your living Province");
            System.out.println("1-TEHRAN\n" +
                    "2-EAST_AZARBAIJAN\n" +
                    "3-WEST_AZARBAIJAN\n" +
                    "4-ARDABIL\n" +
                    "5-ESFAHAN\n" +
                    "6-ALBORZ\n" +
                    "7-ILAM\n" +
                    "8-BUSHEHR\n" +
                    "9-CHAHARMAHAL_VA_BAKHTIARI\n" +
                    "10-SOUTHERN_KHORASAN\n" +
                    "11-KHORASAN_RAZAVI\n" +
                    "12-NORTH_KHORASAN\n" +
                    "13-KHUZESTAN\n" +
                    "14-ZANJAN\n" +
                    "15-SEMNAN\n" +
                    "16-SISTAN_VA_BALUCHESTAN\n" +
                    "17-FARS\n" +
                    "18-QAZVIN\n" +
                    "19-QOM\n" +
                    "20-KURDISTAN\n" +
                    "21-KERMAN\n" +
                    "22-KERMANSHAH\n" +
                    "23-KOHGILOYEH_VA_BOYERAHMAD\n" +
                    "24-GOLESTAN\n" +
                    "25-GUILAN\n" +
                    "26-LORESTAN\n" +
                    "27-MAZANDARAN\n" +
                    "28-CENTRAL\n" +
                    "29-HORMOZGAN\n" +
                    "30-HAMEDAN\n" +
                    "31-YAZD");
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

            if (numberInput >= 1 && numberInput <= 31) {
                break;
            }
        }
        return values[numberInput - 1];
    }

    private static boolean isBankCardNumberValid(String cardNumber) {
        if (cardNumber.length() < 16 || cardNumber.length() > 16) {
            return false;
        }
        List<String> selectedBanks = new ArrayList<>(Arrays.asList("627353",
                "589463", "603799", "628023"));
        String BIN = cardNumber.substring(0, 6);
        if (!selectedBanks.contains(BIN)) {
            return false;
        }

        int checkSum = 0;
        for (int i = 0; i < 16; i += 2) {
            int d1 = (cardNumber.charAt(i) - 48) * 2;
            int d2 = cardNumber.charAt(i + 1) - 48;
            if (d1 > 9) {
                checkSum += d1 - 9;
            } else {
                checkSum += d1;
            }
            checkSum += d2;
        }
        if (checkSum % 10 == 0) {
            return true;
        }
        return false;
    }
}
