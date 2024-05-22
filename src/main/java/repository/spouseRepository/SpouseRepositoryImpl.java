package repository.spouseRepository;

import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import model.Spouse;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SpouseRepositoryImpl extends BaseRepositoryImpl<Spouse, Long>
        implements SpouseRepository{

    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public SpouseRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Spouse> getEntityClass() {
        return Spouse.class;
    }

    @Override
    public String getMyClass() {
        return "Spouse";
    }

    @Override
    public Optional<Spouse> findSpouseStudent(Student student) {
        Session session = sessionFactory.getCurrentSession();
        Query<Spouse> query = session.createQuery("FROM Spouse s " +
                "WHERE s.student=:student", Spouse.class);
        query.setParameter("student", student);

        return Optional.ofNullable(query.getSingleResult());
    }
}
