package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
public class Loan extends BaseEntity<Long> {

    @Column(name = "loan_amount")
    private double loanAmount;

    @Column(name = "loan_date")
    private LocalDate loanDate;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "loan_type")
    @Enumerated(EnumType.STRING)
    private LoanType loanType;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Installment> installments;


    @Override
    public String toString() {
        return "Loan{" +
                "loanAmount=" + loanAmount +
                ", loanDate=" + loanDate +
                ", semester=" + semester +
                ", loanType=" + loanType +
                ", student=" + student +
                ", bankAccount=" + bankAccount +
                ", id=" + id +
                '}';
    }
}
