package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder



@Entity
public class Installment extends BaseEntity<Long> {

    @Column(name = "due_date" )
    private LocalDate dueDate;

    @Column(name = "installment_number" )
    private int installmentNumber;

    @Column(name = "is_paid")
    private Boolean isPayed;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(nullable = false)
    private Double shouldPay;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Override
    public String toString() {
        return "Installment{" +
                "dueDate=" + dueDate +
                ", installmentNumber=" + installmentNumber +
                ", isPayed=" + isPayed +
                ", paymentDate='" + paymentDate + '\'' +
                ", shouldPay=" + shouldPay +
                ", loan=" + loan +
                ", paymentStatus=" + paymentStatus +
                ", id=" + id +
                '}';
    }
}
