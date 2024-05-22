package service.installmentService;

import base.service.BaseService;
import model.Installment;
import model.Loan;

import java.util.List;

public interface InstallmentService extends BaseService<Installment,Long> {
    public List<Installment> findLoanByIdLoan (Loan loan);
}
