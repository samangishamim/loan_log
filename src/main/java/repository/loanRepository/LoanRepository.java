package repository.loanRepository;

import base.repository.BaseRepository;
import model.Loan;
import model.Student;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends BaseRepository<Loan,Long> {
    public List<Loan> findLoanByIdStudent (Student student);
    public Optional<Loan> findStudentWithSemester (Student student , Integer semester);

    Optional<List<Loan>> findMaskanLoanByStudentId(Long studentId);
}
