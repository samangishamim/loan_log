package service.bankAccountService;

import base.service.BaseService;
import model.BankAccount;
import model.Student;

import java.util.Optional;

public interface BankAccountService extends BaseService<BankAccount,Long> {
    public Optional<BankAccount> getBankAccount(Student student, Double cardNumber);
}
