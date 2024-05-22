package utility;

import connection.SessionFactorySingleton;
import org.hibernate.SessionFactory;
import repository.addressRepository.AddressRepository;
import repository.addressRepository.AddressRepositoryImpl;
import repository.bankaccountRepository.BankAccountRepository;
import repository.bankaccountRepository.BankAccountRepositoryImpl;
import repository.installmentRepository.InstallmentRepositoryImpl;
import repository.loanRepository.LoanRepository;
import repository.loanRepository.LoanRepositoryImpl;
import repository.spouseRepository.SpouseRepository;
import repository.spouseRepository.SpouseRepositoryImpl;
import repository.studentRepository.StudentRepository;
import repository.studentRepository.StudentRepositoryImpl;
import service.addressService.AddressService;
import service.addressService.AddressServiceImpl;
import service.bankAccountService.BankAccountService;
import service.bankAccountService.BankAccountServiceImpl;
import service.installmentService.InstallmentService;
import service.installmentService.InstallmentServiceImpl;
import service.loanService.LoanService;
import service.loanService.LoanServiceImpl;
import service.spouseService.SpouseService;
import service.spouseService.SpouseServiceImpl;
import service.studentService.StudentService;
import service.studentService.StudentServiceImpl;

public class ApplicationContext {
    private final static SessionFactory SESSION_FACTORY;

    private final static StudentRepository STUDENT_REPOSITORY;
    private final static StudentService STUDENT_SERVICE;


    private final static SpouseService SPOUSE_SERVICE;
    private final static SpouseRepository SPOUSE_REPOSITORY;


    private final static AddressService ADDRESS_SERVICE;
    private final static AddressRepository ADDRESS_REPOSITORY;


    private final static BankAccountService BANK_ACCOUNT_SERVICE;
    private final static BankAccountRepository BANK_ACCOUNT_REPOSITORY;


    private final static InstallmentService INSTALLMENT_SERVICE;
    private final static InstallmentRepositoryImpl INSTALLMENT_REPOSITORY;


    private final static LoanService LOAN_SERVICE;
    private final static LoanRepository LOAN_REPOSITORY;

    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance();


        STUDENT_REPOSITORY = new StudentRepositoryImpl(SESSION_FACTORY);
        STUDENT_SERVICE = new StudentServiceImpl(STUDENT_REPOSITORY, SESSION_FACTORY);

        SPOUSE_REPOSITORY = new SpouseRepositoryImpl(SESSION_FACTORY);
        SPOUSE_SERVICE = new SpouseServiceImpl(SPOUSE_REPOSITORY, SESSION_FACTORY);

        ADDRESS_REPOSITORY = new AddressRepositoryImpl(SESSION_FACTORY);
        ADDRESS_SERVICE = new AddressServiceImpl(ADDRESS_REPOSITORY, SESSION_FACTORY);

        BANK_ACCOUNT_REPOSITORY = new BankAccountRepositoryImpl(SESSION_FACTORY);
        BANK_ACCOUNT_SERVICE = new BankAccountServiceImpl(BANK_ACCOUNT_REPOSITORY, SESSION_FACTORY);

        INSTALLMENT_REPOSITORY = new InstallmentRepositoryImpl(SESSION_FACTORY);
        INSTALLMENT_SERVICE = new InstallmentServiceImpl(INSTALLMENT_REPOSITORY, SESSION_FACTORY);

        LOAN_REPOSITORY = new LoanRepositoryImpl(SESSION_FACTORY);
        LOAN_SERVICE = new LoanServiceImpl(LOAN_REPOSITORY, SESSION_FACTORY);

    }

    public static StudentService getStudentServices() {
        return STUDENT_SERVICE;
    }

    public static SpouseService getSpouseService() {
        return SPOUSE_SERVICE;
    }

    public static AddressService getAddressService() {
        return ADDRESS_SERVICE;
    }

    public static BankAccountService getBankAccountService() {
        return BANK_ACCOUNT_SERVICE;
    }

    public static InstallmentService getInstallmentService() {
        return INSTALLMENT_SERVICE;
    }

    public static LoanService getLoanService() {
        return LOAN_SERVICE;
    }
}
