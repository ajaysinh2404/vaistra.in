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

public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "state")
    private List<District> districts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "state")
    private List<SubDistrict> subDistricts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "state")
    private List<Village> villages = new ArrayList<>();
}
