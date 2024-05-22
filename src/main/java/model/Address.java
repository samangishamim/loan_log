package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
public class Address  extends BaseEntity<Long> {

    @Column(name = "city" )
    private  String city;

    @Column(name = "postal_code" )
    private  String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Province province;

    @Column(name = "rental_agreement_number", nullable = false )
    private String rentalAgreementNumber;

    @Column(name = "street_addres" )
    private String streetAddress;

    @OneToOne
    @JoinColumn(name = "student_id" )
    private Student student;

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", province=" + province +
                ", rentalAgreementNumber='" + rentalAgreementNumber + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", student=" + student +
                ", id=" + id +
                '}';
    }
}
