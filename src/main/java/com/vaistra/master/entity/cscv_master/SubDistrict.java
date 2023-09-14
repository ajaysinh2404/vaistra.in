package com.vaistra.master.entity.cscv_master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SubDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subDistrictId;

    @Column(name = "district_name")
    private String subDistrictName;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "stateId")
    private State state;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "subDistrict")
    private List<Village> villages = new ArrayList<>();

}
