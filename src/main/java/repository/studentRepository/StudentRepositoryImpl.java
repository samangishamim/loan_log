package repository.studentRepository;

import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import exeption.NotFoundException;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class StudentRepositoryImpl extends BaseRepositoryImpl<Student, Long> implements StudentRepository {
    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public StudentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }

    @Override
    public String getMyClass() {
        return "student";
    }

    @Override
    public Optional<List<Student>> studentSignIn(String nationalId, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery("FROM Student s where" +
                " s.nationalId=:nationalId AND password=:password" , Student.class);
        query.setParameter("nationalId", nationalId );
        query.setParameter( "password" , password );

        List<Student> studentList = query.getResultList();
        return Optional.ofNullable(studentList);
    }

    @Override
    public Optional<List<Student>> studentInfo(String nationalId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery("FROM Student s  " +
                " WHERE s.nationalId=:nationalId" , Student.class);
        query.setParameter("nationalId", nationalId );

        List<Student> studentList = query.getResultList();
        return  Optional.ofNullable(studentList);
    }
}
