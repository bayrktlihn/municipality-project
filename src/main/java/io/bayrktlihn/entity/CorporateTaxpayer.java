package io.bayrktlihn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@Table(name = "corporate_taxpayer")
//@DiscriminatorValue(value = "CORPORATE")
public class CorporateTaxpayer
        extends Taxpayer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taxIdentificationNumber;

    @Override
    public CorporateTaxpayer getTypedTaxpayer() {
        return this;
    }

    @Override
    public boolean isRealTaxpayer() {
        return false;
    }

    @Override
    public boolean isCorporateTaxpayer() {
        return true;
    }
}
