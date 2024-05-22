package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "student")

@Entity
public class Student  extends BaseEntity<Long> {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "entrance_year")
    private LocalDate entranceYear;


    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "grade")
    private String grade;


    @Column(name = "is_married")
    private boolean isMarried;


    @Column(name = "mother_name")
    private String motherName;


    @Column(name = "national_id", unique = true, nullable = false)
    private String nationalId;


    @Column(name = "password")
    private String password;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "university_name")
    private String universityName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable = false)
    private Boolean dormitory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "spouse_id")
    private Spouse spouse;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Loan> loans;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts ;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "section_of_study")
    private Section section;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_university", nullable = false)
    private TypeOfUniversity typeUniversity;



    public void setPassword() {
        String specialChar = getRandomSpecialChar();
        String fname = this.firstName;
        this.password = fname.substring(0, 1).toUpperCase() +
                fname.substring(1) + specialChar +
                this.nationalId.substring(0, 5);
    }
    private String getRandomSpecialChar() {
        List<String> specialChar = List.of("&", "%", "$", "#", "@");
        Random random = new Random();
        int index = random.nextInt(5);
        String selectSpecialChar = specialChar.get(index);
        return selectSpecialChar;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", entranceYear=" + entranceYear +
                ", fatherName='" + fatherName + '\'' +
                ", grade='" + grade + '\'' +
                ", isMarried=" + isMarried +
                ", motherName='" + motherName + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", password='" + password + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", universityName='" + universityName + '\'' +
                ", address=" + address +
                ", dormitory=" + dormitory +
                ", spouse=" + spouse +
                ", loanType=" + loanType +
                ", paymentStatus=" + paymentStatus +
                ", status=" + status +
                ", section=" + section +
                ", typeUniversity=" + typeUniversity +
                ", id=" + id +
                '}';
    }
}
