package repository.addressRepository;

import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import model.Address;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class AddressRepositoryImpl extends BaseRepositoryImpl<Address, Long> implements AddressRepository {
    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public AddressRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Address> getEntityClass() {
        return Address.class;
    }

    @Override
    public String getMyClass() {
        return "Address";
    }

    @Override
    public Optional<Address> getStudentAddress(Student student) {
        Session session = sessionFactory.getCurrentSession();
        Query<Address> query = session.createQuery("FROM Address a WHERE a.student=:student" , Address.class);
        query.setParameter("student",student);

        return Optional.ofNullable(query.getSingleResult());
    }
}
