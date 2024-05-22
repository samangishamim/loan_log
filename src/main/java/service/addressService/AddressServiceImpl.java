package service.addressService;

import base.service.BaseServiceImpl;
import connection.SessionFactorySingleton;
import exeption.NotFoundException;
import model.Address;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.addressRepository.AddressRepository;

import java.util.List;
import java.util.Optional;

public class AddressServiceImpl extends BaseServiceImpl<Address,Long, AddressRepository>
        implements AddressService{
    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public AddressServiceImpl(AddressRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Optional<Address> getStudentAddress(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Address> find = repository.getStudentAddress(student);
            find.orElseThrow(() -> new NotFoundException("Entity not found"));
            session.getTransaction().commit();
            return find;
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
