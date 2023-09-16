package com.vaistra.master.entity.bank_manage;

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

public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankId;

    @Column(name = "bank_short_name")
    private String bankShortName;

    @Column(name = "bank_long_name")
    private String bankLongName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bank")
    private List<BankBranch> branches = new ArrayList<>();

    @Lob
    @Column(name = "bank_logo")
    private byte[] bankLogo;

    @Column(name = "is_active")
    private Boolean isActive;
}
