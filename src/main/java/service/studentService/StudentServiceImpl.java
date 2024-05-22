package service.studentService;

import base.service.BaseServiceImpl;
import connection.SessionFactorySingleton;
import exeption.NotFoundException;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.studentRepository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl extends BaseServiceImpl<Student, Long , StudentRepository>
        implements StudentService{
    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public StudentServiceImpl(StudentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Student> studentSignIn(String nationalId, String password) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<Student>> optionalStudentList = repository.studentSignIn(nationalId, password);
            optionalStudentList.orElseThrow(() -> new NotFoundException("Entity not found"));
            session.getTransaction().commit();
            return optionalStudentList.get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Student> studentInfo(String nationalId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<Student>> optionalStudentList = repository.studentInfo(nationalId);
            optionalStudentList.orElseThrow(() -> new NotFoundException("Entity not found"));
            session.getTransaction().commit();
            return optionalStudentList.get();
        } catch (Exception e) {
            return null;
        }
    }
}
