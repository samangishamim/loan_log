package repository.installmentRepository;

import base.repository.BaseRepository;
import model.Installment;
import model.Loan;
import model.PaymentStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface InstallmentRepository  extends BaseRepository<Installment, Long> {

    List<Installment> findByLoan(Loan loan);

}
