package com.vaistra.master.entity.mines_master;

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

public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipmentId;

    @Column(name = "equipment_name")
    private String equipmentName;
}
