package io.bayrktlihn.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Taxpayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String registrationNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "real_taxpayer_id")
    private RealTaxpayer realTaxpayer;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "corporate_taxpayer_id")
    private CorporateTaxpayer corporateTaxpayer;

}
