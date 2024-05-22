package repository.bankaccountRepository;

import base.repository.BaseRepository;
import model.BankAccount;
import model.Student;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends BaseRepository<BankAccount,Long> {
    public List<BankAccount> bankAccountsStudent(Student student);
    public Optional<BankAccount> getBankAccount(Student student, Double cardNumber);
}
