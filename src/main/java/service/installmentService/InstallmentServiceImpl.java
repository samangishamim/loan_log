package service.installmentService;

import base.service.BaseServiceImpl;
import connection.SessionFactorySingleton;
import model.Installment;
import model.Loan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.installmentRepository.InstallmentRepository;

import java.util.Collections;
import java.util.List;

public class InstallmentServiceImpl extends BaseServiceImpl<Installment,Long, InstallmentRepository>
        implements InstallmentService{

    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public InstallmentServiceImpl(InstallmentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Installment> findLoanByIdLoan(Loan loan) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Installment> find = repository.findByLoan(loan);
            session.getTransaction().commit();
            return find;
        }
        catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
