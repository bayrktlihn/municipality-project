package io.bayrktlihn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "taxpayer")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "taxpayer_type", discriminatorType = DiscriminatorType.STRING)
public abstract class  Taxpayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String registrationNumber;

//    @OneToOne
//    @JoinColumn(name = "real_taxpayer")
//    private RealTaxpayer realTaxpayer;
//
//    @OneToOne
//    @JoinColumn(name = "corporate_taxpayer")
//    private CorporateTaxpayer corporateTaxpayer;

    public abstract <T extends Taxpayer> T getTypedTaxpayer();

    public abstract boolean isRealTaxpayer();
    public abstract boolean isCorporateTaxpayer();

}
