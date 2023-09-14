package com.vaistra.master.entity.cscv_master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer villageId;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "subDistrictId")
    private SubDistrict subDistrict;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "stateId")
    private State state;

    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;
}
