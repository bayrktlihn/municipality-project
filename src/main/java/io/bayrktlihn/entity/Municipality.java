package io.bayrktlihn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(name = "municipality")
@Getter
@Setter
@NoArgsConstructor
public class Municipality implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    private String name;

    private boolean metropolitan;
    private boolean provinceCenter;
    private boolean town;

    @ManyToOne
    @JoinColumn(name = "metropolitan_municipality_id")
    private Municipality metropolitanMunicipality;

    @ManyToOne
    @JoinColumn(name = "province_center_municipality_id")
    private Municipality provinceCenterMunicipality;

    public boolean isDistrict() {
        return district != null;
    }

}
