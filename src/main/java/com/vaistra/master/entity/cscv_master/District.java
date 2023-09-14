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

public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer districtId;

    @Column(name = "city_name")
    private String districtName;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "district")
    private List<SubDistrict> subDistricts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "district")
    private List<Village> villages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "stateId")
    private State state;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;
}
