package service.loanService;

import base.service.BaseService;
import model.Loan;
import model.Student;

import java.util.List;
import java.util.Optional;

public interface LoanService extends BaseService<Loan,Long> {
     List<Loan> findLoanByIdStudent (Student student);
     Optional<Loan> findStudentWithSemester (Student student , Integer semester);
   List<Loan> findMaskanLoanByStudentId(Long id);
}
