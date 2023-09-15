package com.vaistra.master.entity.mines_master;

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
public class Mineral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mineralId;

    @Column(name = "mineral_name")
    private String mineralName;

    @Column(name = "category")
    private String category;

    @Column(name = "atr_name)")
    private String atrName;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "grade")
    private List<String> grade = new ArrayList<>();
}
