package service.spouseService;

import base.service.BaseServiceImpl;
import exeption.NotFoundException;
import model.Spouse;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.spouseRepository.SpouseRepository;

import java.util.Optional;

public class SpouseServiceImpl extends BaseServiceImpl<Spouse, Long , SpouseRepository>
        implements SpouseService {
    public SpouseServiceImpl(SpouseRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Optional<Spouse> findSpouseOfStudent(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Spouse> find = repository.findSpouseStudent(student);
            find.orElseThrow(() -> new NotFoundException("Entity not found"));
            session.getTransaction().commit();
            return find;
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
