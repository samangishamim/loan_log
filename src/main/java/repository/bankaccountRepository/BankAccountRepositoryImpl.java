package repository.bankaccountRepository;

import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import model.Address;
import model.BankAccount;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.addressRepository.AddressRepository;

import java.util.List;
import java.util.Optional;

public class BankAccountRepositoryImpl extends BaseRepositoryImpl<BankAccount, Long> implements BankAccountRepository {
    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public BankAccountRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<BankAccount> getEntityClass() {
        return BankAccount.class;
    }

    @Override
    public String getMyClass() {
        return "BankAccount";
    }

    @Override
    public List<BankAccount> bankAccountsStudent(Student student) {
        Session session = sessionFactory.getCurrentSession();
        Query<BankAccount> query = session.createQuery
                ("FROM bankaccount b WHERE student=:student", BankAccount.class);
        query.setParameter("student", student);

        return query.getResultList();
    }

    @Override
    public Optional<BankAccount> getBankAccount(Student student, Double cardNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query<BankAccount> query = session.createQuery("FROM bankaccount b " +
                "WHERE b.student=:student and b.cardNumber=:cardNumber", BankAccount.class);
        query.setParameter("student", student);
        query.setParameter("cardNumber", cardNumber);

        return Optional.ofNullable(query.getSingleResult());
    }
}
