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

public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "country")
    private List<State> states = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "country")
    private List<District> districts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "country")
    private List<SubDistrict> subDistricts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "country")
    private List<Village> villages = new ArrayList<>();

}
