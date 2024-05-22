package repository.installmentRepository;

import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import model.Installment;
import model.Loan;
import model.PaymentStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class InstallmentRepositoryImpl extends BaseRepositoryImpl<Installment, Long> implements InstallmentRepository{
    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public InstallmentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Installment> getEntityClass() {
        return Installment.class;
    }

    @Override
    public String getMyClass() {
        return "Installment";
    }

    @Override
    public List<Installment> findByLoan(Loan loan) {
        Session session = sessionFactory.getCurrentSession();
        Query<Installment> query = session.createQuery("FROM Installment i WHERE loan=:loan",Installment.class);
        query.setParameter("loan", loan);

        return query.getResultList();
    }
}
