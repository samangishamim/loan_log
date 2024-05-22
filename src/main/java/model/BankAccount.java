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

@Entity(name = "bankaccount")
public class BankAccount extends BaseEntity<Long> {

    @Column(name = "card_number", nullable = false)
    private String cardNumber;


    @Column(name = "cvv2")
    private double cvv2;


    @Column(name = "expiration_date",nullable = false)
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "bankAccount",cascade = CascadeType.ALL)
    private List<Loan> loanList;


    @Override
    public String toString() {
        return "BankAccount{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cvv2=" + cvv2 +
                ", expirationDate=" + expirationDate +
                ", student=" + student +
                ", id=" + id +
                '}';
    }
}

