package service.bankAccountService;

import base.service.BaseServiceImpl;
import connection.SessionFactorySingleton;
import exeption.NotFoundException;
import model.BankAccount;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.bankaccountRepository.BankAccountRepository;

import java.util.List;
import java.util.Optional;

public class BankAccountServiceImpl extends BaseServiceImpl<BankAccount,Long, BankAccountRepository>
        implements BankAccountService{
    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public BankAccountServiceImpl(BankAccountRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Optional<BankAccount> getBankAccount(Student student, Double cardNumber) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<BankAccount> find = repository.getBankAccount(student,cardNumber);
                find.orElseThrow(() -> new NotFoundException("Entity not found"));
            session.getTransaction().commit();
            return find;
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
