package utility;

import model.BankAccount;
import model.Installment;
import model.Loan;
import model.Student;
import service.bankAccountService.BankAccountService;
import service.installmentService.InstallmentService;
import service.loanService.LoanService;
import service.studentService.StudentService;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RefundMenu {
    Scanner scanner = new Scanner(System.in);
    final StudentService studentService = ApplicationContext.getStudentServices();
    final BankAccountService bankAccountService = ApplicationContext.getBankAccountService();
    final InstallmentService installmentService = ApplicationContext.getInstallmentService();
    final LoanService loanService = ApplicationContext.getLoanService();

    public void refundMainMenu(String nationalId) {
        int numberInput = -1;
        while (numberInput != 0) {
            System.out.println("*** Loan Main Menu ***");
            System.out.println("1-Paid Installments - اقساط پرداخت شده");
            System.out.println("2-Unpaid Installments - اقساط پرداخت نشده");
            System.out.println("3-Refund Installments - پرداخت قسط");
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
                case 1 -> paidInstallments(nationalId);
                case 2 -> unpaidInstallments(nationalId);
                case 3 -> payInstallments(nationalId);
                case 0 -> System.out.println();
                default -> System.out.println("Wrong input");
            }
        }
    }

    private void payInstallments(String nationalId) {
        unpaidInstallments(nationalId);
        List<Student> studentList = studentService.studentInfo(nationalId);
        Student student1 = null;
        if (studentList != null) {
            student1 = studentList.get(0);
        } else {
            System.out.println("student not found");
        }
        System.out.println("Please enter id that you want to pay : ");
        Long idLoan = Input.getLongNum();

        System.out.println("Please enter your card number :");
        Double cardNumber = Input.getDoubleNum();
        Optional<BankAccount> bankAccount = bankAccountService.getBankAccount(student1, cardNumber);
        if (bankAccount.isPresent()) {
            System.out.println("Enter your cvv2 number :");
            Double cvv2 = Input.getDoubleNum();
            LocalDate expiration = Input.getLocalDate();
            LocalDate expirationDate = bankAccount.get().getExpirationDate();
            Double cvv2Find = bankAccount.get().getCvv2();
            if (cvv2.equals(cvv2Find) && expiration.equals(expirationDate)) {
                Installment byId = installmentService.findById(idLoan);
                if (byId != null) {
                    LocalDate localDate = LocalDate.now();
                    Installment installment = byId;
                    installment.setIsPayed(true);
                    installment.setPaymentDate(localDate);
                    installmentService.saveOrUpdate(installment);
                } else {
                }
            } else {
                System.out.println("Wrong cvv2 or expiration date, try again");
                refundMainMenu(nationalId);
            }
        } else {
            System.out.println("This bank account not find !!!");
            refundMainMenu(nationalId);
        }
    }

    private void unpaidInstallments(String nationalId) {
        List<Student> studentList = studentService.studentInfo(nationalId);
        Student getStudent=null;
        if (studentList != null){
             getStudent = studentList.get(0);
        }else {
            System.out.println("student not found");
        }


        List<Loan> loanByIdStudent = loanService.findLoanByIdStudent(getStudent);
        for (Loan loan : loanByIdStudent) {
            System.out.println(" id loan : " + loan.getId() + " " + loan.getLoanAmount());
        }
        System.out.println("Enter id loan : ");
        Long idLoan = Input.getLongNum();

        Loan byId = loanService.findById(idLoan);
        Loan loan = byId;

        List<Installment> loanByIdLoan = installmentService.findLoanByIdLoan(loan);
        for (Installment installment : loanByIdLoan) {
            if (installment.getIsPayed().equals(false)) {
                System.out.println(
                        "Id loan : " + installment.getId() +
                                " Number of loan : " + installment.getInstallmentNumber() +
                                " Should pay : " + installment.getShouldPay() +
                                " Due date : " + installment.getDueDate() +
                                " Is payed : " + installment.getIsPayed() +
                                " Payment date : " + installment.getPaymentDate());
            }
        }
    }


    private void paidInstallments(String nationalId) {
        List<Student> studentList = studentService.studentInfo(nationalId);
        Student getStudent=null;
        if (studentList != studentList) {
             getStudent = studentList.get(0);
        }else {
            System.out.println("student not found ");
            return;
        }

        List<Loan> loanByIdStudent = loanService.findLoanByIdStudent(getStudent);
        for (Loan loan : loanByIdStudent) {
            System.out.println(" id loan : " + loan.getId() + " " + loan.getLoanAmount());
        }
        System.out.println("Enter id loan : ");
        Long idLoan = Input.getLongNum();

        Loan byId = loanService.findById(idLoan);
        Loan loan = byId;

        List<Installment> loanByIdLoan = installmentService.findLoanByIdLoan(loan);
        for (Installment installment : loanByIdLoan) {
            if (installment.getIsPayed().equals(true)) {
                System.out.println(
                        "Number of loan : " + installment.getInstallmentNumber() +
                                " Should pay : " + installment.getShouldPay() +
                                " Due date : " + installment.getDueDate() +
                                " Is payed : " + installment.getIsPayed() +
                                " Payment date : " + installment.getPaymentDate());
            }
        }
    }
}
