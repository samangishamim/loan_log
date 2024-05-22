package service.loanService;

import base.service.BaseServiceImpl;
import connection.SessionFactorySingleton;
import exeption.NotFoundException;
import model.Loan;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.loanRepository.LoanRepository;

import java.util.List;
import java.util.Optional;

public class LoanServiceImpl extends BaseServiceImpl<Loan, Long, LoanRepository>
        implements LoanService {

    private final SessionFactory sessionFactory = SessionFactorySingleton.getInstance();

    public LoanServiceImpl(LoanRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Loan> findLoanByIdStudent(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Loan> find = repository.findLoanByIdStudent(student);
            session.getTransaction().commit();
            return find;
        }
    }

    @Override
    public Optional<Loan> findStudentWithSemester(Student student, Integer semester) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Loan> find = repository.findStudentWithSemester(student, semester);
            find.orElseThrow(() -> new NotFoundException("Entity not found"));
            session.getTransaction().commit();
            return find;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Loan> findMaskanLoanByStudentId(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
          Optional<List< Loan>>  optionalLoanList =  repository.findMaskanLoanByStudentId(id);
          optionalLoanList.orElseThrow(() -> new NotFoundException("entity not found")
          );
          if (optionalLoanList.get().isEmpty())
          {
              return null;
          }else
              return optionalLoanList.get();
        }
        catch (Exception e){
            return  null;
        }
    }
}
