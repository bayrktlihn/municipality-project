package io.bayrktlihn.entity;


import io.bayrktlihn.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@Table(name = "real_taxpayer")
@DiscriminatorValue(value = "REAL")
public class RealTaxpayer
        extends Taxpayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String fatherName;
    private String motherName;
    private String identificationNumber;

    @Override
    public RealTaxpayer getTypedTaxpayer() {
        return this;
    }

    @Override
    public boolean isRealTaxpayer() {
        return true;
    }

    @Override
    public boolean isCorporateTaxpayer() {
        return false;
    }
}
