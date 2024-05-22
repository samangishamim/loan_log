package model;

import base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Spouse extends BaseEntity<Long> {


    @Column(name = "spouse_firstname")
    private String spouseFirstName;

    @Column(name = "spouse_lastname")
    private String spouseLastName;

    @Column(name = "spouse_national_id", unique = true, nullable = false)
    private String spouseNationalId;

    @OneToOne
    @JoinColumn(name = "student_id" )
    private Student student;

    @Override
    public String toString() {
        return "Spouse{" +
                "spouseFirstName=" + spouseFirstName +
                ", spouseLastName=" + spouseLastName +
                ", spouseNationalId=" + spouseNationalId +
                ", student=" + student +
                ", id=" + id +
                '}';
    }
}
